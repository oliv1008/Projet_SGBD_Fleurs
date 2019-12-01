package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Map.Entry;

import misc.Settings;
import model.FactureClient;
import model.FactureFournisseur;
import model.Produit;

public class ProduitDAO {

	/*===== ATTRIBUTES =====*/
	private static Connection con;
	private static PreparedStatement s;

	/*===== BUILDER =====*/
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
	 * Ajoute un produit au catalogue
	 * @param produit le produit à ajouter
	 */
	public void insererProduit(Produit produit) throws Exception {
		try {
			s = con.prepareStatement("INSERT INTO Produit (nom, categorie, espece, prix, quantite) VALUES (?, ?, ?, ?, ?)");
			s.setString(1, produit.getNom());
			s.setString(2, produit.getCategorie());
			s.setString(3, produit.getEspece());
			s.setInt(4, produit.getPrix());
			s.setInt(5, produit.getStock());
			s.executeUpdate();
			s.close();
			System.out.println("Ajout d'un produit à la BDD");
		} catch(SQLIntegrityConstraintViolationException e) {
			throw new Exception("Ce produit (" + produit.getNom() + ") est déjà dans la BDD");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Supprime un produit de la BDD
	 * @param nom le nom du produit
	 */
	public void supprimerProduit(String nom) {
		try {
			s = con.prepareStatement("DELETE FROM Produit WHERE nom = ?");
			s.setString(1, nom);
			s.executeUpdate();
			s.close();
			System.out.println("Suppression d'un produit de la BDD");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Met à jour la quantité d'un produit dans la BDD
	 * @param nom le nom du produit
	 * @param modification la modification de quantité, positive ou négative
	 */
	public void mettreAJourQuantite(String nom, int modification) {
		try {
			s = con.prepareStatement("SELECT * FROM Produit WHERE nom = ?", 
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_UPDATABLE);

			s.setString(1, nom);

			ResultSet result = s.executeQuery();

			if(result.next()) {
				int quantite = result.getInt("quantite");
				result.updateInt("quantite", quantite + modification);
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
	 * Pour chaque produit acheté à un fournisseur, met à jour la quantité dans le magasin
	 * @see mettreAJourQuantite
	 * @param facture la facture fournisseur
	 * TODO : EST CE QUE Y'AURAIT BESOIN D'UN COMMIT ICI ???
	 */
	public void appliquerFactureFournisseur(FactureFournisseur facture) {
		for(Entry<Produit, Integer> entry : facture.getProduits().entrySet()) {
			mettreAJourQuantite(entry.getKey().getNom(), entry.getValue());
		}
	}

	/**
	 * Pour chaque produit vendu à un client, met à jour la quantité dans le magasin
	 * @see mettreAJourQuantite
	 * @param facture la facture client
	 * TODO : EST CE QUE Y'AURAIT BESOIN D'UN COMMIT ICI ???
	 */
	public void appliquerFactureClient(FactureClient facture) {
		for(Entry<Produit, Integer> entry : facture.getProduits().entrySet()) {
			mettreAJourQuantite(entry.getKey().getNom(), -entry.getValue());
		}
	}
}

