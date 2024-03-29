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
import model.FactureFournisseur;
import model.Produit;

public class FactureFournisseurDAO {

	/*===== ATTRIBUTES =====*/
	/**
	 * La connection avec la base de données dont l'adresse est définie dans misc/Settings.
	 */
	private static Connection con;
	/**
	 * Un buffer contenant une requête SQL.
	 */
	private static PreparedStatement s;

	/*===== BUILDER =====*/
	/**
	 * Charge le driver jdbc et initialise une connection à la base de données dont l'adresse est définie
	 * dans misc/Settings.
	 */
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
			System.exit(1);
		}
	}

	/*===== METHODS =====*/

	/**
	 * Ajoute une facture fournisseur à la BDD.
	 * @param facture la facture à ajouter.
	 */
	public void insererFacture(FactureFournisseur facture) {
		try {
			s = con.prepareStatement(
					"INSERT INTO FactureFournisseur (idFournisseur, montant) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

			s.setInt(1, facture.getIdFournisseur());
			s.setInt(2, facture.getMontantFacture());
			s.executeUpdate();

			ResultSet result = s.getGeneratedKeys();

			int idFactureFournisseur = 0;

			if(result.next()) {
				idFactureFournisseur = result.getInt(1);
			}

			s.close();
			
			ProduitDAO produitDAO = new ProduitDAO();

			for(Entry<Produit, Integer> entry : facture.getProduits().entrySet()) {
				s = con.prepareStatement(
						"INSERT INTO ProduitsFactureFournisseur (idFactureFournisseur, nomProduit, quantite) VALUES (?, ?, ?)");

				s.setInt(1, idFactureFournisseur);
				s.setString(2, entry.getKey().getNom());
				s.setInt(3, entry.getValue());
				s.executeUpdate();
				
				s.close();
				
				produitDAO.mettreAJourQuantite(entry.getKey().getNom(), +entry.getValue());		
			}

//			con.commit();	// Est ce que y'a vrmt besoin du commit ?

			System.out.println("Ajout d'une facture fournisseur à la BDD");

		} catch(SQLException e) {
			e.printStackTrace();
//			con.rollback();
		}
	}
	
	/**
	 * Renvoie la liste de toutes les factures fournisseurs enregistrées dans la base de données.
	 * @return Une ArrayList contenant toutes les factures fournisseurs enregistrées dans la base de données.
	 */
	public ArrayList<FactureFournisseur> listeFactureFournisseur(){
		
		ArrayList<FactureFournisseur> factures = new ArrayList<FactureFournisseur>();

		try {
			s = con.prepareStatement("SELECT FactureFournisseur.idFactureFournisseur, Fournisseur.idFournisseur "
					+ "FROM Fournisseur, FactureFournisseur "
					+ "WHERE Fournisseur.idFournisseur = FactureFournisseur.idFournisseur");

			ResultSet result = s.executeQuery();
			
			int idFacture = 0;

			while(result.next()){   
				
				idFacture = result.getInt("FactureFournisseur.idFactureFournisseur");
				
				FactureFournisseur facture = new FactureFournisseur();
				facture.setIdFacture(idFacture);
				facture.setIdFournisseur(result.getInt("Fournisseur.idFournisseur"));
				
				PreparedStatement s2 = con.prepareStatement("SELECT nomProduit, quantite "
						+ "FROM ProduitsFactureFournisseur WHERE idFactureFournisseur = ?");
				
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
