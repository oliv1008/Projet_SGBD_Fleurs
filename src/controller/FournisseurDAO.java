package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import misc.Settings;
import model.Fournisseur;
import model.Produit;

public class FournisseurDAO {

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
	public FournisseurDAO() {
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
	 * Ajoute un fournisseur à la BDD
	 * @param fournisseur le client à ajouter
	 */
	public void insererFournisseur(Fournisseur fournisseur) throws Exception {
		try {
			s = con.prepareStatement("INSERT INTO Fournisseur (nom, prenom, adresse) VALUES (?, ?, ?)");
			s.setString(1, fournisseur.getNom());
			s.setString(2, fournisseur.getPrenom());
			s.setString(3, fournisseur.getAdresse());
			s.executeUpdate();
			s.close();
			System.out.println("Ajout d'un fournisseur à la BDD");
		} catch(SQLIntegrityConstraintViolationException e) {
			throw new Exception("Ce fournisseur (" + fournisseur.getNom() + ") est déjà dans la BDD");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Supprime un fournisseur de la BDD
	 * @param idFournisseur l'id du fournisseur
	 */
	public void supprimerFournisseur(int idFournisseur) {
		try {
			s = con.prepareStatement("DELETE FROM Fournisseur WHERE idFournisseur = ?");
			s.setInt(1, idFournisseur);
			s.executeUpdate();
			s.close();
			System.out.println("Suppression d'un fournisseur de la BDD");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Renvoie la liste de tout les fournisseurs enregistrés dans la base de données.
	 * @return Une ArrayList contenant tout les fournisseurs enregistrés dans la base de données.
	 */
	public ArrayList<Fournisseur> listeFournisseurs(){
		ArrayList<Fournisseur> fournisseurs = new ArrayList<Fournisseur>();
		
		try {
			s = con.prepareStatement("SELECT * FROM Fournisseur");
			ResultSet result = s.executeQuery();

			while(result.next()){         
				fournisseurs.add(new Fournisseur(
						result.getInt("idFournisseur"), 
						result.getString("nom"), 
						result.getString("prenom"),
						result.getString("adresse")));
			}

			return fournisseurs;

		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Renvoie la liste de tous les produits fourni par un fournisseur enregistrés dans la base de données.
	 * @param idFournisseur L'ID du fournisseur dont on veut obtenir la liste des produits qu'il fournit
	 * @return Une ArrayList contenant tous les produits fourni par le fournisseur dont l'ID correspond au paramètres idFournisseur dans la base de données.
	 */
	public ArrayList<Produit> listeProduitsParFournisseur(int idFournisseur){
		ArrayList<Produit> produits = new ArrayList<Produit>();
		
		try {
			s = con.prepareStatement("SELECT nom, categorie, espece, FournitProduit.prix FROM FournitProduit, Produit "
					+ "WHERE FournitProduit.nomProduit = Produit.nom "
					+ "AND idFournisseur = ?");
			
			s.setInt(1, idFournisseur);
			
			ResultSet result = s.executeQuery();

			while(result.next()){   
				
				produits.add(new Produit(
						result.getString("nom"), 
						result.getString("categorie"), 
						result.getString("espece"),
						result.getInt("FournitProduit.prix")));
			}

			return produits;

		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * Recherche un fournisseur par son ID.
	 * 
	 * @param id L'ID du fournisseur à rechercher
	 * @return Un Fournisseur dont l'ID correspond à celui passé en paramètre.
	 */
	public Fournisseur getFournisseurById(int id) {
		try {
			s = con.prepareStatement("SELECT * FROM Fournisseur WHERE idFournisseur = ?");
			s.setInt(1, id);

			ResultSet result = s.executeQuery();

			while(result.next()){   

				return new Fournisseur(
						result.getInt("idFournisseur"), 
						result.getString("nom"), 
						result.getString("prenom"),
						result.getString("adresse"));
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
