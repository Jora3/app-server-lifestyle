package com.lifestile.system.client;

import java.io.Serializable;
import java.util.ArrayList;

public class Entreprise implements Serializable {
    private String entrepriseId;
    private String nomEntreprise;
    private ArrayList<InfoAddress> infoAddresses;
    private Domaine domaine;
    private String pass;
    private boolean etat;

    public Entreprise(String entrepriseId, String nomEntreprise, ArrayList<InfoAddress> infoAddresses, Domaine domaine, String pass, boolean etat) {
        this.setEntrepriseId(entrepriseId);
        this.setNomEntreprise(nomEntreprise);
        this.setInfoAddresses(infoAddresses);
        this.setDomaine(domaine);
        this.setPass(pass);
        this.setEtat(etat);
    }

    public Entreprise() {
    }

    public String getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(String entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public ArrayList<InfoAddress> getInfoAddresses() {
        return infoAddresses;
    }

    public void setInfoAddresses(ArrayList<InfoAddress> infoAddresses) {
        this.infoAddresses = infoAddresses;
    }

    public Domaine getDomaine() {
        return domaine;
    }

    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }
}
