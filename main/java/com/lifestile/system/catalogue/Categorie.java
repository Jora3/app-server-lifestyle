package com.lifestile.system.catalogue;

import java.io.Serializable;

public class Categorie implements Serializable {
    private String categorieId;
    private String nomCategorie;
    private String description;

    public Categorie(String categorieId, String nomCategorie, String description) {
        this.setCategorieId(categorieId);
        this.setNomCategorie(nomCategorie);
        this.setDescription(description);
    }

    public Categorie() {
    }

    public String getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(String categorieId) {
        this.categorieId = categorieId;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
