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
	private static Connection con;
	private static PreparedStatement s;

	/*===== BUILDER =====*/
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
