package com.lifestile.system.catalogue;

import com.lifestile.system.client.Entreprise;

import java.io.Serializable;
import java.util.ArrayList;

public class Bazar implements Serializable {
    private String bazarId;
    private Produit produit;
    private String description;
    private String photo;
    private Entreprise entreprise;

    public Bazar(String bazarId, Produit produit, String description, String photo, Entreprise entreprise) {
        this.setBazarId(bazarId);
        this.setProduit(produit);
        this.setDescription(description);
        this.setPhoto(photo);
        this.setEntreprise(entreprise);
    }

    public Bazar() {
    }

    public String getBazarId() {
        return bazarId;
    }

    public void setBazarId(String bazarId) {
        this.bazarId = bazarId;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }
}
