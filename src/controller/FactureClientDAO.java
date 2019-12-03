package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map.Entry;

import misc.Settings;
import model.FactureClient;
import model.Produit;

public class FactureClientDAO {

	/*===== ATTRIBUTES =====*/
	private static Connection con;
	private static PreparedStatement s;

	/*===== BUILDER =====*/
	public FactureClientDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e) {
			System.err.println("Impossible de charger le driver JDBC");
			e.printStackTrace();
			System.exit(1);
		}

		try {
			con = DriverManager.getConnection(Settings.pathToDatabase, "root", "");
		}
		catch(SQLException e) {
			System.err.println("Impossible de se connecter au serveur SQL");
			System.exit(1);
		}
	}

	/*===== METHODS =====*/

	/**
	 * Ajoute un facture client à la BDD
	 * @param facture la facture à ajouter
	 */
	public void insererFacture(FactureClient facture) {
		try {
			s = con.prepareStatement(
					"INSERT INTO FactureClient (idClient, montant) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

			s.setInt(1, facture.getIdClient());
			s.setInt(2, facture.getMontantFacture());
			s.executeUpdate();

			ResultSet result = s.getGeneratedKeys();

			int idFactureClient = 0;

			if(result.next()) {
				idFactureClient = result.getInt(1);
			}

			s.close();

			ProduitDAO produitDAO = new ProduitDAO();

			for(Entry<Produit, Integer> entry : facture.getProduits().entrySet()) {
				s = con.prepareStatement(
						"INSERT INTO ProduitsFactureClient (idFactureClient, nomProduit, quantite) VALUES (?, ?, ?)");

				s.setInt(1, idFactureClient);
				s.setString(2, entry.getKey().getNom());
				s.setInt(3, entry.getValue());
				s.executeUpdate();

				s.close();

				produitDAO.mettreAJourQuantite(entry.getKey().getNom(), -entry.getValue());	
			}

			//			con.commit();	// Est ce que y'a vrmt besoin du commit ?

			System.out.println("Ajout d'une facture client à la BDD");

		} catch(SQLException e) {
			e.printStackTrace();
			//			con.rollback(); ?
		}
	}

	public ArrayList<FactureClient> listeFactureClient(){
		
		ArrayList<FactureClient> factures = new ArrayList<FactureClient>();

		try {
			s = con.prepareStatement("SELECT FactureClient.idFactureClient, Client.idClient "
					+ "FROM Client, FactureClient "
					+ "WHERE Client.idClient = FactureClient.idClient");

			ResultSet result = s.executeQuery();
			
			int idFacture = 0;

			while(result.next()){   
				
				idFacture = result.getInt("FactureClient.idFactureClient");
				
				FactureClient facture = new FactureClient();
				facture.setIdFacture(result.getInt("FactureClient.idFactureClient"));
				facture.setIdClient(result.getInt("Client.idClient"));
				
				PreparedStatement s2 = con.prepareStatement("SELECT nomProduit, quantite "
						+ "FROM ProduitsFactureClient WHERE idFactureClient = ?");
				
				s2.setInt(1, idFacture);
				
				ResultSet result2 = s2.executeQuery();
				
				while(result2.next()) {
					Produit p = (new ProduitDAO()).getProduitByName(result2.getString("nomProduit"));
					facture.ajouterProduit(p, result2.getInt("quantite"));
				}
				
				factures.add(facture);		
			}

			return factures;

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
