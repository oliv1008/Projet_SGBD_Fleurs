package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Fenêtre principale
 */
public class MainView extends JFrame {

	/*===== ATTRIBUTES =====*/
	JPanel center;					// Affichage central, contenant l'onglet selectionné

	JButton factureClient;			// Bouton "Facture client" 
	JButton factureFournisseur;		// Bouton "Facture fournisseur"
	JButton historiqueFacture;		// Bouton "Historique facture"

	/*===== BUILDER =====*/
	public MainView() {

		setTitle("Gestion factures");
		setMinimumSize(new Dimension(530, 590));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);

		setLayout(new BorderLayout());

		JPanel north = new JPanel();
		north.setLayout(new GridLayout(0, 3));

		// Ajout du bouton "Facture client"
		factureClient = new JButton("Facture client");
		factureClient.setPreferredSize(new Dimension(160, 30));
		factureClient.setBackground(new Color(174, 174, 174)); 
		factureClient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				center.removeAll(); 
				center.add(new FactureClientPanel()); 
				center.validate();
				factureClient.setBackground(new Color(174, 174, 174)); 
				factureFournisseur.setBackground(null); 
				historiqueFacture.setBackground(null);	
			}
		});

		// Ajout du bouton "Facture fournisseur"
		factureFournisseur = new JButton("Facture fournisseur");
		factureFournisseur.setPreferredSize(new Dimension(160, 30));
		factureFournisseur.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				center.removeAll(); 
				center.add(new FactureFournisseurPanel()); 
				center.validate();
				factureClient.setBackground(null); 
				factureFournisseur.setBackground(new Color(174, 174, 174)); 
				historiqueFacture.setBackground(null);	
			}
		});

		// Ajout du bouton "Historique factures"
		historiqueFacture = new JButton("Historique factures");
		historiqueFacture.setPreferredSize(new Dimension(160, 30));
		historiqueFacture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				center.removeAll(); 
				center.add(new HistoriqueFacturePanel()); 
				center.validate();
				factureClient.setBackground(null); 
				factureFournisseur.setBackground(null); 
				historiqueFacture.setBackground(new Color(174, 174, 174));	
			}
		});

		north.add(factureClient);
		north.add(factureFournisseur);
		north.add(historiqueFacture);

		add(north, BorderLayout.NORTH);

		center = new JPanel();
		center.add(new FactureClientPanel());
		add(center, BorderLayout.CENTER);

		setVisible(true);
	}
}
