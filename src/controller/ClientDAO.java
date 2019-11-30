package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import misc.Settings;
import model.Client;
import model.Produit;

public class ClientDAO {

	/*===== ATTRIBUTES =====*/
	private static Connection con;
	private static PreparedStatement s;

	/*===== BUILDER =====*/
	public ClientDAO() {
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
	 * Ajoute un client à la BDD
	 * @param client le client à ajouter
	 */
	public void insererClient(Client client) throws Exception {
		try {
			s = con.prepareStatement("INSERT INTO Client (nom, prenom, adresse, reduction) VALUES (?, ?, ?, ?)");
			s.setString(1, client.getNom());
			s.setString(2, client.getPrenom());
			s.setString(3, client.getAdresse());
			s.setFloat(4, client.getReduction());
			s.executeUpdate();
			s.close();
			System.out.println("Ajout d'un client à la BDD");
		} catch(SQLIntegrityConstraintViolationException e) {
			throw new Exception("Ce client (" + client.getNom() + ") est déjà dans la BDD");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Supprime un client de la BDD
	 * @param idClient l'id du client
	 */
	public void supprimerClient(int idClient) {
		try {
			s = con.prepareStatement("DELETE FROM Client WHERE idClient = ?");
			s.setInt(1, idClient);
			s.executeUpdate();
			s.close();
			System.out.println("Suppression d'un client de la BDD");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Client> listeClients(){
		ArrayList<Client> clients = new ArrayList<Client>();
		
		try {
			s = con.prepareStatement("SELECT * FROM Client");
			
			ResultSet result = s.executeQuery();

			while(result.next()){   
				
				clients.add(new Client(
						result.getInt("idClient"), 
						result.getString("nom"), 
						result.getString("prenom"), 
						result.getString("adresse"), 
						result.getFloat("reduction")));
			}

			return clients;

		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
