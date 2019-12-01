package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

			for(Entry<Produit, Integer> entry : facture.getProduits().entrySet()) {
				s = con.prepareStatement(
						"INSERT INTO ProduitsFactureClient (idFactureClient, nomProduit, quantite) VALUES (?, ?, ?)");

				s.setInt(1, idFactureClient);
				s.setString(2, entry.getKey().getNom());
				s.setInt(3, entry.getValue());
				s.executeUpdate();

				s.close();
			}

//			con.commit();	// Est ce que y'a vrmt besoin du commit ?

			System.out.println("Ajout d'une facture client à la BDD");

		} catch(SQLException e) {
			e.printStackTrace();
//			con.rollback(); ?
		}

	}
}
