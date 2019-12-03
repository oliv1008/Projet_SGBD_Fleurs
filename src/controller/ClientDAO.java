package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import misc.Settings;
import model.Client;

public class ClientDAO {

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
			System.exit(1);
		}
	}

	/*===== METHODS =====*/
	/**
	 * Renvoie la liste de tout les clients enregistrés dans la base de données.
	 * @return Une ArrayList contenant tout les clients enregistrés dans la base de données.
	 */
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
	
	/**
	 * Recherche un client par son ID.
	 * 
	 * @param id L'ID du client à rechercher
	 * @return Un Client dont l'ID correspond à celui passé en paramètre.
	 */
	public Client getClientById(int id) {
		try {
			s = con.prepareStatement("SELECT * FROM Client WHERE idClient = ?");
			s.setInt(1, id);

			ResultSet result = s.executeQuery();

			while(result.next()){   

				return new Client(
						result.getInt("idClient"), 
						result.getString("nom"), 
						result.getString("prenom"),
						result.getString("adresse"),
						result.getFloat("reduction"));
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
