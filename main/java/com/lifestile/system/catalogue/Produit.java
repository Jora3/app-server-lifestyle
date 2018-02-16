package com.lifestile.system.catalogue;


import com.lifestile.system.client.Entreprise;

import java.io.Serializable;

public class Produit implements Serializable {
    private String    produitId;
    private String    nomProduit;
    private Categorie categorie;
    private SousCategorie sousCategorie;
    private Type type;
    private Details details;
    private double prix;
    private String photo;
    private String dateAjout;
    private Entreprise proprietaire;

    public String getDescription() {
        return nomProduit + " a été ajouté le " + dateAjout + ". De couleur " + details.getCouleur() + " et de taille " + details.getTaille().getValeurTaille()
                + ", il coûte " + prix + " euros. Il est classé dans le catégorie "
                + categorie.getNomCategorie() + ", sous-catégorie " + sousCategorie.getNomSousCategorie()
                + " et type " + type.getNomType();
    }

    public Produit(String produitId, String nomProduit, Categorie categorie, SousCategorie sousCategorie, Type type, Details details, double prix, String photo, String dateAjout, Entreprise proprietaire) {
        this.setProduitId(produitId);
        this.setNomProduit(nomProduit);
        this.setCategorie(categorie);
        this.setSousCategorie(sousCategorie);
        this.setType(type);
        this.setDetails(details);
        this.setPrix(prix);
        this.setPhoto(photo);
        this.setDateAjout(dateAjout);
        this.setProprietaire(proprietaire);
    }

    public Produit() {
    }

    public String getProduitId() {
        return produitId;
    }

    public void setProduitId(String produitId) {
        this.produitId = produitId;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public SousCategorie getSousCategorie() {
        return sousCategorie;
    }

    public void setSousCategorie(SousCategorie sousCategorie) {
        this.sousCategorie = sousCategorie;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(String dateAjout) {
        this.dateAjout = dateAjout;
    }

    public Entreprise getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Entreprise proprietaire) {
        this.proprietaire = proprietaire;
    }
}