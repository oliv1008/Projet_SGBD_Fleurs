package model;

import java.util.HashMap;
import java.util.Map.Entry;

public class FactureFournisseur {

	/*===== ATTRIBUTES =====*/
	private int idFacture;
	private int idFournisseur;
	private int montantFacture;
	private HashMap<Produit, Integer> produits;
	
	/*===== BUILDER =====*/
	public FactureFournisseur() {
		super();
		montantFacture = 0;
		produits = new HashMap<Produit, Integer>();
	}
	
	/*===== GETTERS AND SETTERS =====*/
	public void setIdFacture(int idFacture) {
		this.idFacture = idFacture;
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
	
	public int calculMontantFacture() {
		for(Entry<Produit, Integer> entry : produits.entrySet()) {
			montantFacture += entry.getValue() * entry.getKey().getPrix();
		}
		return montantFacture;
	}
	
	@Override
	public String toString() {
		return "Facture #" + idFacture + " (Fournisseur #" + idFournisseur + ")";
	}
	
}
