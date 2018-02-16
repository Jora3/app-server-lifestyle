package com.lifestile.system.catalogue;

import java.io.Serializable;

public class SousCategorie implements Serializable {
    private String sousCategorieId;
    private String nomSousCategorie;
    private Categorie categorie;
    private String description;

    public SousCategorie(String sousCategorieId, String nomSousCategorie, Categorie categorie, String description) {
        this.setSousCategorieId(sousCategorieId);
        this.setNomSousCategorie(nomSousCategorie);
        this.setCategorie(categorie);
        this.setDescription(description);
    }

    public SousCategorie() {
    }

    public String getSousCategorieId() {
        return sousCategorieId;
    }

    public void setSousCategorieId(String sousCategorieId) {
        this.sousCategorieId = sousCategorieId;
    }

    public String getNomSousCategorie() {
        return nomSousCategorie;
    }

    public void setNomSousCategorie(String nomSousCategorie) {
        this.nomSousCategorie = nomSousCategorie;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
