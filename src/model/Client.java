package model;

public class Client {

	private int idClient;
	private String nom;
	private String prenom;
	private String adresse;
	private float reduction;
	
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public float getReduction() {
		return reduction;
	}
	public void setReduction(float reduction) {
		this.reduction = reduction;
	}
}
