package model;

import java.util.HashMap;

public class FactureFournisseur {

	private int idFactureFournisseur;
	private int idFournisseur;
	private int montantFacture;
	private HashMap<Produit, Integer> produits;
	
	public FactureFournisseur(int idFactureFournisseur, int idFournisseur, int montantFacture, HashMap<Produit, Integer> produits) {
		super();
		this.idFactureFournisseur = idFactureFournisseur;
		this.idFournisseur = idFournisseur;
		this.produits = produits;
		this.montantFacture = montantFacture;
	}
	
	public int getIdFactureFournisseur() {
		return idFactureFournisseur;
	}
	
	public void setIdFactureFournisseur(int idFactureFournisseur) {
		this.idFactureFournisseur = idFactureFournisseur;
	}
	
	public int getIdFournisseur() {
		return idFournisseur;
	}
	
	public void setIdFournisseur(int idFournisseur) {
		this.idFournisseur = idFournisseur;
	}
	
	public HashMap<Produit, Integer> getProduits() {
		return produits;
	}
	
	public void setProduits(HashMap<Produit, Integer> produits) {
		this.produits = produits;
	}
	
	public int getMontantFacture() {
		return montantFacture;
	}
	
	public void setMontantFacture(int montantFacture) {
		this.montantFacture = montantFacture;
	}
	
	
}
