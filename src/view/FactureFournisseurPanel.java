package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import controller.FactureFournisseurDAO;
import controller.FournisseurDAO;
import model.FactureFournisseur;
import model.Fournisseur;
import model.Produit;

/**
 * Panel de création de factures fournisseur
 */
public class FactureFournisseurPanel extends JPanel {

	/*===== ATTRIBUTES =====*/
	JLog log = new JLog();						// Panel d'affichage de la facture
	JComboBox<Fournisseur> comboBoxFournisseur;	// ComboBox de sélection du fournisseur
	JComboBox<Produit> comboBoxProduit;			// ComboBox de sélection des produits
	SpinnerNumberModel spinnerModel;			// Spinner de sélection de la quantité
	JButton addButton;							// Bouton "Ajouter"
	JButton creerFacture;						// Bouton "Créer facture"

	FactureFournisseur facture = new FactureFournisseur();	// Facture

	/*===== BUILDER =====*/
	public FactureFournisseurPanel() {

		setLayout(new GridLayout(2, 2, 10, 10));

		addFournisseurChoicePanel();

		addFactureInfosPanel();

		addProduitsChoicePanel();

		addActionsPanel();
	}

	// ----- MENU DE CHOIX DU CLIENT -----------------------------------
	private void addFournisseurChoicePanel() {
		JPanel fournisseurChoicePanel = new JPanel(new BorderLayout());
		fournisseurChoicePanel.setPreferredSize(new Dimension(250, 250));

		// Label "Choix du fournisseur"
		JLabel label = new JLabel("Choix du fournisseur", SwingConstants.CENTER);
		label.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		fournisseurChoicePanel.add(label, BorderLayout.NORTH);

		// ComboBox de sélection du fournisseurs
		comboBoxFournisseur = new JComboBox<Fournisseur>();
		comboBoxFournisseur.setPreferredSize(new Dimension(240, 25));

		// On ajoute tous les fournisseurs
		ArrayList<Fournisseur> fournisseurs = (new FournisseurDAO()).listeFournisseurs();
		comboBoxFournisseur.addItem(null);
		for(Fournisseur f : fournisseurs) {
			comboBoxFournisseur.addItem(f);
		}

		// On ajoute un action listener qui met à jour la facture
		comboBoxFournisseur.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(comboBoxFournisseur.getSelectedItem() == null) {
					log.clear();
					addButton.setEnabled(false);
				}
				else {
					// On met à jour l'affichage de la facture
					log.clear();
					log.append("Fournisseur : " + comboBoxFournisseur.getSelectedItem().toString());
					log.append("");
					log.append("Produits achetés :");

					// On met à jour l'objet facture
					facture.setIdFournisseur(((Fournisseur)comboBoxFournisseur.getSelectedItem()).getIdFournisseur());

					addButton.setEnabled(true);
				}
				updateComboBoxProduit();
			}
		});

		JPanel center = new JPanel();
		center.add(comboBoxFournisseur);

		fournisseurChoicePanel.add(center, BorderLayout.CENTER);

		add(fournisseurChoicePanel);
	}

	// ----- AFFICHAGE DE LA FACTURE -----------------------------------
	private void addFactureInfosPanel() {
		JPanel factureInfosPanel = new JPanel(new BorderLayout());
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
		JPanel produitsChoicePanel = new JPanel(new BorderLayout());
		produitsChoicePanel.setPreferredSize(new Dimension(250, 250));

		// Label "Produits disponibles"
		JLabel label = new JLabel("Produits disponibles", SwingConstants.CENTER);
		label.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		produitsChoicePanel.add(label, BorderLayout.NORTH);

		// ComboBox d'affichage des produits
		comboBoxProduit = new JComboBox<Produit>();
		comboBoxProduit.setPreferredSize(new Dimension(240, 25));

		// On ajoute tous les produits
		updateComboBoxProduit();

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
			@Override
			public void actionPerformed(ActionEvent event) {
				Produit produit = (Produit)(comboBoxProduit.getSelectedItem());
				Integer quantite = (Integer)spinnerModel.getValue();
				
				// On met à jour l'affichage de la facture
				log.append("- " + produit + "\t(x" + quantite + ")");
				
				comboBoxProduit.setSelectedIndex(0);

				// On met à jour l'object facture
				facture.ajouterProduit(produit, quantite);
			}
		});

		south.add(addButton);

		produitsChoicePanel.add(south, BorderLayout.SOUTH);

		add(produitsChoicePanel);
	}

	// ----- BOUTONS  -----------------------------------
	private void addActionsPanel() {
		JPanel actionsPanel = new JPanel(new BorderLayout());
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
			@Override
			public void actionPerformed(ActionEvent event) {
				// On met à jour l'objet facture
				facture.calculMontantFacture();

				// On met à jour l'affichage de la facture
				log.append("");
				log.append("Montant total = " + facture.getMontantFacture() + "€");
				log.append("");
				log.append("Facture enregistrée");

				// On ajoute la facture à la BDD
				(new FactureFournisseurDAO()).insererFacture(facture);

				creerFacture.setEnabled(false);
			}
		});

		center.add(creerFacture);

		// Bouton "Remettre à zéro"
		JButton resetFacture = new JButton("Remettre à zéro");
		resetFacture.setPreferredSize(new Dimension(240, 25));

		resetFacture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				facture = new FactureFournisseur();
				log.clear();
				comboBoxFournisseur.setSelectedIndex(0);
				creerFacture.setEnabled(true);
			}
		});

		center.add(resetFacture);

		actionsPanel.add(center, BorderLayout.CENTER);

		add(actionsPanel);
	}

	/**
	 * Met à jour la liste des produits proposés par un fournisseur
	 */
	private void updateComboBoxProduit() {
		// Reset la comboBox
		comboBoxProduit.removeAllItems();
		
		// Si un fournisseur est selectionné
		if(comboBoxFournisseur.getSelectedItem() !=  null) {
			// Récupère son id
			int idFournisseur = ((Fournisseur)comboBoxFournisseur.getSelectedItem()).getIdFournisseur();
			
			// Ajoute chaque produit qu'il fournit à la comboBox
			ArrayList<Produit> produits = (new FournisseurDAO()).listeProduitsParFournisseur(idFournisseur);
			for(Produit p : produits) {
				comboBoxProduit.addItem(p);
			}
		}		
	}
}
