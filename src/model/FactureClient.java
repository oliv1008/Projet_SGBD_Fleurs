package model;

import java.util.HashMap;

public class FactureClient {

	private int idFactureClient;
	private int idClient;
	private int montantFacture;
	private HashMap<Produit, Integer> produits;
	
	public FactureClient(int idFactureClient, int idClient, int montantFacture, HashMap<Produit, Integer> produits) {
		super();
		this.idFactureClient = idFactureClient;
		this.idClient = idClient;
		this.montantFacture = montantFacture;
		this.produits = produits;
	}

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
