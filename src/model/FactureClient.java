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
	
	/**
	 * Ajoute un produit à la facture d'un client.
	 * Si le produit existe déjà, augmente la quantité voulu, sinon le créer et l'initialise d'une quantité.
	 * @param produit Le produit à ajouter à la facture
	 * @param quantite La quantité du produit à ajouter
	 */
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
	
	/**
	 * Calcul le montant de la facture sans prendre en compte ni TVA ni réduction (i.e : la somme du prix de chaque Produit
	 * contenu dans la facture)
	 * @return Le montant de la facture sans réduction ni TVA.
	 */
	public int calculMontantBrut() {
		for(Entry<Produit, Integer> entry : produits.entrySet()) {
			montantFacture += entry.getValue() * entry.getKey().getPrix();
		}
		return montantFacture;
	}
	
	/**
	 * Calcul le montant de la facture en prenant en compte une réduction.
	 * @param reduction La réduction à appliqué sur le montant total.
	 * @return Le montant de la facture en prenant en compte une réduction.
	 */
	public int calculReduction(float reduction) {
		montantFacture = (int)(montantFacture - reduction * montantFacture);
		return montantFacture;
	}
	
	/**
	 * Ajoute la TVA à 15 pourcent sur le montant total de la facture.
	 * @return Le montant final de la facture avec la TVA ajouté.
	 */
	public int calculTVA() {
		montantFacture = (int)(montantFacture * 1.15);
		return montantFacture;
	}
	
	@Override
	public String toString() {
		return "Facture #" + idFacture + " (Client #" + idClient + ")";
	}
	
}
