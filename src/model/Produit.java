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
	
	public String getCategorie() {
		return categorie;
	}
	
	public String getEspece() {
		return espece;
	}
	
	public int getPrix() {
		return prix;
	}
	
	public int getStock() {
		return stock;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public void changeStock(int modif) {
		this.stock += modif;
	}
	
	public String toString() {
		return nom + " (" + prix + "€/u)";
	}
	
	
}
