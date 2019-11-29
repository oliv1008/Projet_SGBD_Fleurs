package model;

import java.util.ArrayList;

public class FactureFournisseur {

	private int idFactureFournisseur;
	private int idFournisseur;
	private ArrayList<Produit> produits;
	private float montantFacture;
	
	public FactureFournisseur(int idFactureFournisseur, int idFournisseur, ArrayList<Produit> produits,
			float montantFacture) {
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
	public ArrayList<Produit> getProduits() {
		return produits;
	}
	public void setProduits(ArrayList<Produit> produits) {
		this.produits = produits;
	}
	public float getMontantFacture() {
		return montantFacture;
	}
	public void setMontantFacture(float montantFacture) {
		this.montantFacture = montantFacture;
	}
	
	
}
