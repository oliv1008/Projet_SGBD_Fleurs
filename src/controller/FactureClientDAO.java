package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import misc.Settings;

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
			e.printStackTrace();
			System.exit(1);
		}
	}

	/*===== METHODS =====*/
	
	/**
	 * Ajoute un livre au catalogue
	 * @param isbn 
	 * @param name 
	 * @param author
	 */
	public void insertBook(int isbn, String name, String author) throws Exception {
		try {
			s = con.prepareStatement("INSERT INTO Book (isbn, name, author) VALUES (?, ?, ?)");
			s.setInt(1, isbn);
			s.setString(2, name);
			s.setString(3, author);
			s.executeUpdate();
			s.close();
			System.out.println("Ajout d'un livre à la BDD");
		} catch(SQLIntegrityConstraintViolationException e) {
			throw new Exception("Ce livre (" + isbn + ") est déjà dans la bibliothèque");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Supprime un livre de la BDD
	 * @param isbn l'isbn du livre
	 */
	public void deleteBook(int isbn) {
		try {
			s = con.prepareStatement("DELETE FROM Book WHERE isbn = " + isbn);
			s.executeUpdate();
			s.close();
			System.out.println("Suppression d'un livre de la BDD");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
