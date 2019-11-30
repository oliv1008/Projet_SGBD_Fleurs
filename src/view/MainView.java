package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainView extends JFrame implements ActionListener {
	
	JPanel center;

	JButton factureClient;
	JButton factureFournisseur;

	public MainView() {

		setTitle("Main App");
		setMinimumSize(new Dimension(530, 590));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);

		setLayout(new BorderLayout());

		JPanel north = new JPanel();

		factureClient = new JButton("Créer facture client");
		factureClient.setPreferredSize(new Dimension(240, 30));
		factureClient.addActionListener(this);
		factureClient.setBackground(new Color(174, 174, 174)); 

		factureFournisseur = new JButton("Créer facture fournisseur");
		factureFournisseur.setPreferredSize(new Dimension(240, 30));
		factureFournisseur.addActionListener(this);

		north.add(factureClient);
		north.add(factureFournisseur);

		add(north, BorderLayout.NORTH);

		center = new JPanel();

		center.add(new FactureClientPanel());

		add(center, BorderLayout.CENTER);

		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Créer facture client"	:	
			center.removeAll(); center.add(new FactureClientPanel()); center.validate();
			factureClient.setBackground(new Color(174, 174, 174)); factureFournisseur.setBackground(null); 
			
			break;
		case "Créer facture fournisseur"	: 	
			center.removeAll(); center.add(new FactureFournisseurPanel()); center.validate();
			factureFournisseur.setBackground(new Color(174, 174, 174)); factureClient.setBackground(null); 
			
			break;
		}

	}
}
