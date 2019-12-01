package model;

import java.util.HashMap;
import java.util.Map.Entry;

public class FactureClient {

	/*===== ATTRIBUTES =====*/
	private int idClient;
	private int montantFacture;
	private HashMap<Produit, Integer> produits;
	
	/*===== BUILDER =====*/
	public FactureClient() {
		super();
		montantFacture = 0;
		produits = new HashMap<Produit, Integer>();
	}
	
	/*===== GETTERS AND SETTERS =====*/
	public int getIdClient() {
		return idClient;
	}
	
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	
	public HashMap<Produit, Integer> getProduits() {
		return produits;
	}
	
	public void ajouterProduit(Produit produit, Integer quantite) {
		if(produits.containsKey(produit)) {
			int ancienneQte = produits.get(produit);
			produits.put(produit, quantite + ancienneQte);
		}
		else {
			produits.put(produit, quantite);
		}		
	}
	
	public int getMontantFacture() {
		return montantFacture;
	}
	
	public void calculMontantFacture() {
		for(Entry<Produit, Integer> entry : produits.entrySet()) {
			montantFacture += entry.getValue() * entry.getKey().getPrix();
		}
	}
	
}
