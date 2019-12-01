package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import controller.ClientDAO;
import controller.FactureClientDAO;
import controller.ProduitDAO;
import model.Client;
import model.FactureClient;
import model.Produit;

public class FactureClientPanel extends JPanel {

	JLog log = new JLog();
	JComboBox<Client> comboBoxClient;
	JComboBox<Produit> comboBoxProduit;
	SpinnerNumberModel spinnerModel;
	
	JButton addButton;
	JButton creerFacture;
	
	FactureClient facture = new FactureClient();

	public FactureClientPanel() {

		setLayout(new GridLayout(2, 2, 10, 10));

		addClientChoicePanel();

		addFactureInfosPanel();

		addProduitsChoicePanel();

		addActionsPanel();
	}

	// ----- MENU DE CHOIX DU CLIENT -----------------------------------
	private void addClientChoicePanel() {
		JPanel clientChoicePanel = new JPanel();
		clientChoicePanel.setLayout(new BorderLayout());
		clientChoicePanel.setPreferredSize(new Dimension(250, 250));

		// Label "Choix du client"
		JLabel label = new JLabel("Choix du client", SwingConstants.CENTER);
		label.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		clientChoicePanel.add(label, BorderLayout.NORTH);

		// ComboBox de sélection du client
		comboBoxClient = new JComboBox<Client>();
		comboBoxClient.setPreferredSize(new Dimension(240, 25));

		// On ajoute tous les clients
		ArrayList<Client> clients = (new ClientDAO()).listeClients();
		comboBoxClient.addItem(null);
		for(Client c : clients) {
			comboBoxClient.addItem(c);
		}

		// On ajoute un action listener qui met à jour la facture
		comboBoxClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(comboBoxClient.getSelectedItem() == null) {
					log.clear();
					addButton.setEnabled(false);
				}
				else {
					// On met à jour l'affichage de la facture
					log.clear();
					log.append("Client : " + comboBoxClient.getSelectedItem().toString());
					log.append("");
					log.append("Produits achetés :");
					addButton.setEnabled(true);
					
					// On met à jour l'objet facture
					facture.setIdClient(((Client)comboBoxClient.getSelectedItem()).getIdClient());
				}
			}
		});

		JPanel center = new JPanel();
		center.add(comboBoxClient);

		clientChoicePanel.add(center, BorderLayout.CENTER);

		add(clientChoicePanel);
	}

	// ----- AFFICHAGE DE LA FACTURE -----------------------------------
	private void addFactureInfosPanel() {
		JPanel factureInfosPanel = new JPanel();
		factureInfosPanel.setLayout(new BorderLayout());
		factureInfosPanel.setPreferredSize(new Dimension(250, 250));

		// Label "Informations facture"
		JLabel label = new JLabel("Informations facture", SwingConstants.CENTER);
		label.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		factureInfosPanel.add(label, BorderLayout.NORTH);

		// Affichage de la facture (cf. JLog)
		factureInfosPanel.add(log, BorderLayout.CENTER);

		add(factureInfosPanel);
	}

	// ----- MENU DE CHOIX DES PRODUITS -----------------------------------
	private void addProduitsChoicePanel() {
		JPanel produitsChoicePanel = new JPanel();
		produitsChoicePanel.setLayout(new BorderLayout());
		produitsChoicePanel.setPreferredSize(new Dimension(250, 250));

		// Label "Produits disponibles"
		JLabel label = new JLabel("Produits disponibles", SwingConstants.CENTER);
		label.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		produitsChoicePanel.add(label, BorderLayout.NORTH);

		// ComboBox d'affichage des produits
		comboBoxProduit = new JComboBox<Produit>();
		comboBoxProduit.setPreferredSize(new Dimension(240, 25));

		// On ajoute tous les produits
		ArrayList<Produit> produits = (new ProduitDAO()).listeProduits();
		for(Produit p : produits) {
			comboBoxProduit.addItem(p);
		}

		// On ajoute un action listener qui met à jour le Spinner 
		comboBoxProduit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int quantite = ((Produit)(comboBoxProduit.getSelectedItem())).getStock();
				if(quantite == 0) {							// Si la quantité vaut 0 :
					spinnerModel.setValue(0);				// On fixe le spinner à 0
					addButton.setEnabled(false);			// Et on desactive le bouton "Ajouter"
				}											
				else {										// Sinon :	
					spinnerModel.setValue(1);				// On remet le spinner à 1								
					spinnerModel.setMaximum(quantite);		// On fixe le maximum à cette quantité
					addButton.setEnabled(true);				// Et on active le bouton "Ajouter"
				}
			}
		});

		JPanel center = new JPanel();
		center.add(comboBoxProduit);

		produitsChoicePanel.add(center, BorderLayout.CENTER);

		JPanel south = new JPanel();

		// Label "Quantité"
		JLabel label2 = new JLabel("Quantité : ");
		label2.setPreferredSize(new Dimension(90, 25));
		south.add(label2);

		// NumberSpinner représentant la quantité achetée
		spinnerModel = new SpinnerNumberModel(1, 1, null, 1);
		JSpinner spinner = new JSpinner(spinnerModel);
		spinner.setPreferredSize(new Dimension(40, 25));
		south.add(spinner);

		// Bouton "Ajouter"
		addButton = new JButton("Ajouter");
		addButton.setPreferredSize(new Dimension(100, 25));
		addButton.setEnabled(false);

		// On ajoute un action listener qui met à jour la facture
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// On met à jour l'affichage de la facture
				log.append("- " + comboBoxProduit.getSelectedItem().toString() + "\t(x" + spinnerModel.getValue() + ")");
				Produit produit = (Produit)(comboBoxProduit.getSelectedItem());
				Integer quantite = (Integer)spinnerModel.getValue();
				produit.changeStock(-quantite);
				comboBoxProduit.setSelectedIndex(0);
				
				// On met à jour l'object facture
				facture.ajouterProduit(produit, quantite);
			}
		});

		// Pour forcer l'appel du action listener de comboBoxProduit
		comboBoxProduit.setSelectedIndex(0);

		south.add(addButton);

		produitsChoicePanel.add(south, BorderLayout.SOUTH);

		add(produitsChoicePanel);
	}

	// ----- BOUTONS  -----------------------------------
	private void addActionsPanel() {
		JPanel actionsPanel = new JPanel();
		actionsPanel.setLayout(new BorderLayout());
		actionsPanel.setPreferredSize(new Dimension(250, 250));

		// Label "Actions"
		JLabel label = new JLabel("Actions", SwingConstants.CENTER);
		label.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		actionsPanel.add(label, BorderLayout.NORTH);

		JPanel center = new JPanel();

		// Bouton "Créer facture"
		creerFacture = new JButton("Créer facture");
		creerFacture.setPreferredSize(new Dimension(240, 25));
		
		creerFacture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// On met à jour l'objet facture
				facture.calculMontantFacture();
				
				// On met à jour l'affichage de la facture
				log.append("");
				log.append("Montant total = " + facture.getMontantFacture() + "€");
				log.append("");
				log.append("Facture payée le " + (LocalDateTime.now()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'à' HH:mm")));
			
				// On ajoute la facture à la BDD
				(new FactureClientDAO()).insererFacture(facture);
				
				creerFacture.setEnabled(false);
			}
		});

		center.add(creerFacture);

		// Bouton "Remettre à zéro"
		JButton resetFacture = new JButton("Remettre à zéro");
		resetFacture.setPreferredSize(new Dimension(240, 25));
		
		resetFacture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				facture = new FactureClient();
				log.clear();
				comboBoxClient.setSelectedIndex(0);
				comboBoxProduit.setSelectedIndex(0);
				
				creerFacture.setEnabled(true);
			}
		});
		
		center.add(resetFacture);

		actionsPanel.add(center, BorderLayout.CENTER);

		add(actionsPanel);
	}

}
