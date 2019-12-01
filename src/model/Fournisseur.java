package model;

public class Fournisseur {

	private int idFournisseur;
	private String nom;
	private String prenom;
	private String adresse;

	public Fournisseur(int idFournisseur, String nom, String prenom, String adresse) {
		super();
		this.idFournisseur = idFournisseur;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
	}
	
	public int getIdFournisseur() {
		return idFournisseur;
	}
	
	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getAdresse() {
		return adresse;
	}
	
	public String toString() {
		return idFournisseur + " - " + prenom + " " + nom;
	}

}
