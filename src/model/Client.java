package model;

public class Client {

	private int idClient;
	private String nom;
	private String prenom;
	private String adresse;
	private float reduction;
	
	public Client(int idClient, String nom, String prenom, String adresse, float reduction) {
		super();
		this.idClient = idClient;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.reduction = reduction;
	}

	public int getIdClient() {
		return idClient;
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
	
	public float getReduction() {
		return reduction;
	}
	
	@Override
	public String toString() {
		return idClient + " - " + prenom + " " + nom;
	}
}
