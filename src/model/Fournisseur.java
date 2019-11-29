package model;

import java.util.ArrayList;

public class Fournisseur {

	private int idFournisseur;
	private String Fournisseur;
	private String prenom;
	private String adresse;
	private ArrayList<Produit> produits;
	
	public int getIdFournisseur() {
		return idFournisseur;
	}
	public void setIdFournisseur(int idFournisseur) {
		this.idFournisseur = idFournisseur;
	}
	public String getFournisseur() {
		return Fournisseur;
	}
	public void setFournisseur(String fournisseur) {
		Fournisseur = fournisseur;
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
	public ArrayList<Produit> getProduits() {
		return produits;
	}
	public void setProduits(ArrayList<Produit> produits) {
		this.produits = produits;
	}
}
