package model;

import java.util.ArrayList;

public class Fournisseur {

	private int idFournisseur;
	private String nom;
	private String prenom;
	private String adresse;
	private ArrayList<Produit> produits;
	
	
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
	public void setIdFournisseur(int idFournisseur) {
		this.idFournisseur = idFournisseur;
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
	public ArrayList<Produit> getProduits() {
		return produits;
	}
	public void setProduits(ArrayList<Produit> produits) {
		this.produits = produits;
	}
}
