package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ClientDAO;
import controller.ProduitDAO;
import model.Client;
import model.Produit;

public class FactureFournisseurPanel extends JPanel {

	public FactureFournisseurPanel() {
		
		setLayout(new GridLayout(2, 2));
		
		addClientChoicePanel();
		
		addFactureInfosPanel();
		
		addProduitsChoicePanel();
	}
	
	private void addClientChoicePanel() {
		JPanel clientChoicePanel = new JPanel();
		clientChoicePanel.setLayout(new GridLayout(2, 0));
		
		JLabel label = new JLabel("Choix du client");
		clientChoicePanel.add(label);
		
		JComboBox<Client> comboBox = new JComboBox<Client>();
		ArrayList<Client> clients = (new ClientDAO()).listeClients();
		
		for(Client c : clients) {
			comboBox.addItem(c);
		}
		
		clientChoicePanel.add(comboBox);
		
		add(clientChoicePanel);
	}
	
	private void addFactureInfosPanel() {
		JPanel factureInfosPanel = new JPanel();
		factureInfosPanel.setLayout(new BorderLayout());
		
		JLabel label = new JLabel("Informations facture");
		factureInfosPanel.add(label, BorderLayout.NORTH);
		
		JLog log = new JLog();
		factureInfosPanel.add(log, BorderLayout.CENTER);
	
		add(factureInfosPanel);
	}
	
	private void addProduitsChoicePanel() {
		JPanel produitsChoicePanel = new JPanel();
		produitsChoicePanel.setLayout(new BorderLayout());
		
		JLabel label = new JLabel("Produits disponibles");
		produitsChoicePanel.add(label, BorderLayout.NORTH);
		
		JComboBox<Produit> comboBox = new JComboBox<Produit>();
		ArrayList<Produit> produits = (new ProduitDAO()).listeProduits();
		
		for(Produit p : produits) {
			comboBox.addItem(p);
		}
		
		produitsChoicePanel.add(comboBox, BorderLayout.CENTER);
		
		JPanel south = new JPanel();
		
		JLabel label2 = new JLabel("Quantité : ");
		south.add(label2);
		
		JTextField quantiteTextField = new JTextField();
		south.add(quantiteTextField);
		
		JButton button = new JButton("Ajouter");
		south.add(button);
		
		produitsChoicePanel.add(south, BorderLayout.SOUTH);
		
		add(produitsChoicePanel);
	}
}
