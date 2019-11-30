package model;

public class Produit {

	private String nom;
	private String categorie;
	private String espece;
	private int prix;
	private int stock;
	
	public Produit(String nom, String categorie, String espece, int prix, int stock) {
		super();
		this.nom = nom;
		this.categorie = categorie;
		this.espece = espece;
		this.prix = prix;
		this.stock = stock;
	}
	
	public Produit(String nom, String categorie, String espece, int prix) {
		super();
		this.nom = nom;
		this.categorie = categorie;
		this.espece = espece;
		this.prix = prix;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getCategorie() {
		return categorie;
	}
	
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
	public String getEspece() {
		return espece;
	}
	
	public void setEspece(String espece) {
		this.espece = espece;
	}
	
	public int getPrix() {
		return prix;
	}
	
	public void setPrix(int prix) {
		this.prix = prix;
	}
	
	public int getStock() {
		return stock;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public String toString() {
		return nom;
	}
	
	
}
