package model;

import java.util.HashMap;
import java.util.Map.Entry;

public class FactureClient {

	/*===== ATTRIBUTES =====*/
	private int idFacture;
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
	public void setIdFacture(int idFacture) {
		this.idFacture = idFacture;
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
	
	public int calculMontantBrut() {
		for(Entry<Produit, Integer> entry : produits.entrySet()) {
			montantFacture += entry.getValue() * entry.getKey().getPrix();
		}
		return montantFacture;
	}
	
	public int calculReduction(float reduction) {
		montantFacture = (int)(montantFacture - reduction * montantFacture);
		return montantFacture;
	}
	
	public int calculTVA() {
		montantFacture = (int)(montantFacture * 1.15);
		return montantFacture;
	}
	
	@Override
	public String toString() {
		return "Facture #" + idFacture + " (Client #" + idClient + ")";
	}
	
}
