package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import misc.Settings;
import model.Produit;

public class ProduitDAO {

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
	public ProduitDAO() {
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
	 * Met à jour la quantité d'un produit dans la BDD
	 * @param nom le nom du produit
	 * @param modification la modification de quantité, positive ou négative
	 */
	public void mettreAJourQuantite(String nom, int modification) {
		try {
			System.out.println("on entre dans majQte");
			s = con.prepareStatement("SELECT * FROM Produit WHERE nom = ?", 
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_UPDATABLE);

			s.setString(1, nom);

			ResultSet result = s.executeQuery();

			if(result.next()) {
				int quantite = result.getInt("quantite");
				System.out.println("qte actuelle = " + quantite);
				result.updateInt("quantite", quantite + modification);
				System.out.println("qte new = " + quantite + modification);
				result.updateRow();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Renvoie la liste de tous les produits du magasin
	 * @return la liste des produits
	 */
	public ArrayList<Produit> listeProduits(){
		ArrayList<Produit> produits = new ArrayList<Produit>();

		try {
			s = con.prepareStatement("SELECT * FROM Produit");

			ResultSet result = s.executeQuery();

			while(result.next()){   

				produits.add(new Produit(
						result.getString("nom"), 
						result.getString("categorie"), 
						result.getString("espece"),
						result.getInt("prix"),
						result.getInt("quantite")));
			}

			return produits;

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * Recherche un Produit par son nom.
	 * 
	 * @param nom Le nom du produit à rechercher.
	 * @return Un Produit dont le nom correspond à celui passé en paramètre.
	 */
	public Produit getProduitByName(String nom) {
		try {
			s = con.prepareStatement("SELECT * FROM Produit WHERE nom = ?");
			s.setString(1, nom);

			ResultSet result = s.executeQuery();

			while(result.next()){   

				return new Produit(
						result.getString("nom"), 
						result.getString("categorie"), 
						result.getString("espece"),
						result.getInt("prix"),
						result.getInt("quantite"));
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}

