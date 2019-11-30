package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map.Entry;

import misc.Settings;
import model.FactureFournisseur;
import model.Produit;

public class FactureFournisseurDAO {

	/*===== ATTRIBUTES =====*/
	private static Connection con;
	private static PreparedStatement s;

	/*===== BUILDER =====*/
	public FactureFournisseurDAO() {
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
			e.printStackTrace();
			System.exit(1);
		}
	}

	/*===== METHODS =====*/

	/**
	 * Ajoute un facture fournisseur à la BDD
	 * @param facture la facture à ajouter
	 */
	public void insererFacture(FactureFournisseur facture) {
		try {
			s = con.prepareStatement(
					"INSERT INTO FactureFournisseur (idFournisseur, montant) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			s.setInt(1, facture.getIdFournisseur());
			s.setInt(2, facture.getMontantFacture());
			s.executeUpdate();
			
			int idFactureFournisseur = s.getGeneratedKeys().getInt(1);
			
			s.close();
			
			for(Entry<Produit, Integer> entry : facture.getProduits().entrySet()) {
					s = con.prepareStatement(
							"INSERT INTO ProduitsFactureFournisseur (idFactureFournisseur, nomProduit, quantite) VALUES (?, ?, ?)");
					
					s.setInt(1, idFactureFournisseur);
					s.setString(2, entry.getKey().getNom());
					s.setInt(3, entry.getValue());
					s.executeUpdate();
					
					s.close();
			}
			
			con.commit();	// Est ce que y'a vrmt besoin du commit ?
			
			System.out.println("Ajout d'une facture fournisseur à la BDD");
			
		} catch(SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
}
