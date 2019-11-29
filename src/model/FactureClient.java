package model;

import java.util.ArrayList;

public class FactureClient {

	private int idFactureClient;
	private int idClient;
	private ArrayList<Produit> produits;
	private float montantFacture;
	
	public int getIdFactureClient() {
		return idFactureClient;
	}
	public void setIdFactureClient(int idFactureClient) {
		this.idFactureClient = idFactureClient;
	}
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
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
