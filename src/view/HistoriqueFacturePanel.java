package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.ClientDAO;
import controller.FactureClientDAO;
import controller.FactureFournisseurDAO;
import controller.FournisseurDAO;
import model.FactureClient;
import model.FactureFournisseur;
import model.Produit;

/**
 * Panel d'affichage des factures
 */
public class HistoriqueFacturePanel extends JPanel {

	/*===== ATTRIBUTES =====*/
	JLog log = new JLog();									// Panel d'affichage de la facture	
	JComboBox<FactureClient> comboBoxFacClient;				// ComboBox de sélection de la facture client
	JComboBox<FactureFournisseur> comboBoxFacFournisseur;	// ComboBox de sélection de la facture produit

	FactureClient factureClient = new FactureClient();					// Facture client
	FactureFournisseur factureFournisseur = new FactureFournisseur();	// Facture fournisseur

	/*===== BUILDER =====*/
	public HistoriqueFacturePanel() {

		setLayout(new GridLayout(2, 2, 10, 10));

		addFacClientChoicePanel();

		addFactureInfosPanel();

		addFacFournisseurChoicePanel();
	}

	// ----- CHOIX FACTURE CLIENT -----------------------------------
	private void addFacClientChoicePanel() {
		JPanel facClientChoicePanel = new JPanel(new BorderLayout());
		facClientChoicePanel.setPreferredSize(new Dimension(250, 250));

		// Label "Facture client"
		JLabel label = new JLabel("Facture client", SwingConstants.CENTER);
		label.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		facClientChoicePanel.add(label, BorderLayout.NORTH);

		// ComboBox de sélection du client
		comboBoxFacClient = new JComboBox<FactureClient>();
		comboBoxFacClient.setPreferredSize(new Dimension(240, 25));

		// On ajoute tous les clients
		ArrayList<FactureClient> factures = (new FactureClientDAO()).listeFactureClient();
		comboBoxFacClient.addItem(null);
		for(FactureClient f : factures) {
			comboBoxFacClient.addItem(f);
		}

		// On ajoute un action listener qui met à jour la facture
		comboBoxFacClient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(comboBoxFacClient.getSelectedItem() == null) {
					log.clear();
				}
				else {
					// On récupère la facture client
					FactureClient f = (FactureClient)(comboBoxFacClient.getSelectedItem());
					
					// On affiche les informations sur le client
					log.clear();
					log.append("Client : " + (new ClientDAO()).getClientById(f.getIdClient()));
					log.append("");
					log.append("Produits achetés :");
					
					// On affiche les informations sur les produits
					for(Entry<Produit, Integer> entry : f.getProduits().entrySet()) {
						log.append("- " + entry.getKey() + "\t(x" + entry.getValue() + ")");
					}
					
					// On affiche les informations sur le prix total
					int reduction = (int)(100.0*(new ClientDAO()).getClientById(f.getIdClient()).getReduction());
					log.append("");
					log.append("Montant brut : \t\t" + f.calculMontantBrut() + "€");
					log.append("Avec réduction (- " + reduction + "%) : \t" + f.calculReduction((new ClientDAO()).getClientById(f.getIdClient()).getReduction()) + "€");
					log.append("Montant total (TVA 15%) : \t" + f.calculTVA() + "€");
				}
			}
		});

		JPanel center = new JPanel();
		center.add(comboBoxFacClient);

		facClientChoicePanel.add(center, BorderLayout.CENTER);

		add(facClientChoicePanel);
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

//	----- CHOIX FACTURE FOURNISSEUR -----------------------------------
	private void addFacFournisseurChoicePanel() {
		JPanel facFournisseurChoicePanel = new JPanel(new BorderLayout());
		facFournisseurChoicePanel.setPreferredSize(new Dimension(250, 250));

		// Label "Facture fournisseur"
		JLabel label = new JLabel("Facture fournisseur", SwingConstants.CENTER);
		label.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		facFournisseurChoicePanel.add(label, BorderLayout.NORTH);

		// ComboBox de sélection du fournisseur
		comboBoxFacFournisseur = new JComboBox<FactureFournisseur>();
		comboBoxFacFournisseur.setPreferredSize(new Dimension(240, 25));

		// On ajoute tous les fournisseur
		ArrayList<FactureFournisseur> factures = (new FactureFournisseurDAO()).listeFactureFournisseur();
		comboBoxFacFournisseur.addItem(null);
		for(FactureFournisseur f : factures) {
			comboBoxFacFournisseur.addItem(f);
		}

		// On ajoute un action listener qui met à jour la facture
		comboBoxFacFournisseur.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(comboBoxFacFournisseur.getSelectedItem() == null) {
					log.clear();
				}
				else {
					// On récupère la facture fournisseur
					FactureFournisseur f = (FactureFournisseur)(comboBoxFacFournisseur.getSelectedItem());
					
					// On affiche les informations sur le fournisseur
					log.clear();
					log.append("Fournisseur : " + (new FournisseurDAO()).getFournisseurById(f.getIdFournisseur()));
					log.append("");
					log.append("Produits achetés :");
					
					// On affiche les informations sur les produits
					for(Entry<Produit, Integer> entry : f.getProduits().entrySet()) {
						log.append("- " + entry.getKey() + "\t(x" + entry.getValue() + ")");
					}
					
					// On affiche les informations sur le prix total
					log.append("");
					log.append("Montant total : \t" + f.calculMontantFacture() + "€");
				}
			}
		});

		JPanel center = new JPanel();
		center.add(comboBoxFacFournisseur);

		facFournisseurChoicePanel.add(center, BorderLayout.CENTER);

		add(facFournisseurChoicePanel);
	}
}
