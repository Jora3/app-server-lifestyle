package com.lifestile.system.catalogue;

import com.lifestile.system.client.Personne;

import java.io.Serializable;
import java.util.ArrayList;

public class Styles implements Serializable {
    private String   styleId;
    private Produit produit;
    private Personne personne;

    public Styles(String styleId, Produit produit, Personne personne) {
        setStyleId(styleId);
        setProduit(produit);
        setPersonne(personne);
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }
}
