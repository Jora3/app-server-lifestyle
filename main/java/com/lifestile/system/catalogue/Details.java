package com.lifestile.system.catalogue;

import java.io.Serializable;

public class Details implements Serializable {
    private String couleur;
    private Taille taille;

    public Details(String couleur, Taille taille) {
        this.setCouleur(couleur);
        this.setTaille(taille);
    }

    public Details() {
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Taille getTaille() {
        return taille;
    }

    public void setTaille(Taille taille) {
        this.taille = taille;
    }
}
