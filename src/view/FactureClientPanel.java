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

import controller.ClientDAO;
import controller.ProduitDAO;
import model.Client;
import model.Produit;

public class FactureClientPanel extends JPanel implements ActionListener {

	JLog log = new JLog();
	JComboBox<Client> comboBoxClient;
	JComboBox<Produit> comboBoxProduit;
	SpinnerNumberModel spinnerModel;
	JButton addButton;

	public FactureClientPanel() {

		setLayout(new GridLayout(2, 2, 10, 10));

		addClientChoicePanel();

		addFactureInfosPanel();

		addProduitsChoicePanel();

		addActionsPanel();
	}

	private void addClientChoicePanel() {
		JPanel clientChoicePanel = new JPanel();
		clientChoicePanel.setLayout(new BorderLayout());
		clientChoicePanel.setPreferredSize(new Dimension(250, 250));

		JLabel label = new JLabel("Choix du client", SwingConstants.CENTER);
		label.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		clientChoicePanel.add(label, BorderLayout.NORTH);

		comboBoxClient = new JComboBox<Client>();
		comboBoxClient.setPreferredSize(new Dimension(240, 25));

		ArrayList<Client> clients = (new ClientDAO()).listeClients();
		comboBoxClient.addItem(null);
		for(Client c : clients) {
			comboBoxClient.addItem(c);
		}
		
		comboBoxClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(comboBoxClient.getSelectedItem() == null) {
					log.clear();
					addButton.setEnabled(false);
				}
				else {
					log.clear();
					log.append("Client : " + comboBoxClient.getSelectedItem().toString());
					log.append("");
					log.append("Produits achetés :");
					addButton.setEnabled(true);
				}
			}
		});

		JPanel center = new JPanel();
		center.add(comboBoxClient);

		clientChoicePanel.add(center, BorderLayout.CENTER);

		add(clientChoicePanel);
	}

	private void addFactureInfosPanel() {
		JPanel factureInfosPanel = new JPanel();
		factureInfosPanel.setLayout(new BorderLayout());
		factureInfosPanel.setPreferredSize(new Dimension(250, 250));

		JLabel label = new JLabel("Informations facture", SwingConstants.CENTER);
		label.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		factureInfosPanel.add(label, BorderLayout.NORTH);

		factureInfosPanel.add(log, BorderLayout.CENTER);

		add(factureInfosPanel);
	}

	private void addProduitsChoicePanel() {
		JPanel produitsChoicePanel = new JPanel();
		produitsChoicePanel.setLayout(new BorderLayout());
		produitsChoicePanel.setPreferredSize(new Dimension(250, 250));

		JLabel label = new JLabel("Produits disponibles", SwingConstants.CENTER);
		label.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		produitsChoicePanel.add(label, BorderLayout.NORTH);

		comboBoxProduit = new JComboBox<Produit>();
		comboBoxProduit.setPreferredSize(new Dimension(240, 25));

		ArrayList<Produit> produits = (new ProduitDAO()).listeProduits();
		for(Produit p : produits) {
			comboBoxProduit.addItem(p);
		}

		JPanel center = new JPanel();
		center.add(comboBoxProduit);

		produitsChoicePanel.add(center, BorderLayout.CENTER);

		JPanel south = new JPanel();

		JLabel label2 = new JLabel("Quantité : ");
		label2.setPreferredSize(new Dimension(90, 25));
		south.add(label2);

		spinnerModel = new SpinnerNumberModel(1, 1, null, 1);
		JSpinner spinner = new JSpinner(spinnerModel);
		spinner.setPreferredSize(new Dimension(40, 25));
		south.add(spinner);

		addButton = new JButton("Ajouter");
		addButton.setPreferredSize(new Dimension(100, 25));
		addButton.setEnabled(false);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				log.append("- " + comboBoxProduit.getSelectedItem().toString() + "\t(x" + spinnerModel.getValue() + ")");
			}
		});
		south.add(addButton);

		produitsChoicePanel.add(south, BorderLayout.SOUTH);

		add(produitsChoicePanel);
	}

	private void addActionsPanel() {
		JPanel actionsPanel = new JPanel();
		actionsPanel.setLayout(new BorderLayout());
		actionsPanel.setPreferredSize(new Dimension(250, 250));

		JLabel label = new JLabel("Actions", SwingConstants.CENTER);
		label.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		actionsPanel.add(label, BorderLayout.NORTH);

		JPanel center = new JPanel();

		JButton creerFacture = new JButton("Créer facture");
		creerFacture.setPreferredSize(new Dimension(240, 25));
		creerFacture.addActionListener(this);
		center.add(creerFacture);

		JButton resetFacture = new JButton("Remettre à zéro");
		resetFacture.setPreferredSize(new Dimension(240, 25));
		resetFacture.addActionListener(this);
		center.add(resetFacture);

		actionsPanel.add(center, BorderLayout.CENTER);

		add(actionsPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
