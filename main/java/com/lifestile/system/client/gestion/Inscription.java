package com.lifestile.system.client.gestion;

import com.lifestile.system.client.Domaine;
import com.lifestile.system.client.Entreprise;
import com.lifestile.system.client.InfoAddress;

public class Inscription {
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String sexe;
    private String societe;
    private InfoAddress infoAddress;
    private Domaine domaine;
    private String pass1;
    private String pass2;

    public Inscription(String societe, InfoAddress infoAddress, Domaine domaine, String pass1, String pass2) throws Exception {
        if(pass1.equals(pass2)) {
            this.setSociete(societe);
            this.setInfoAddress(infoAddress);
            this.setDomaine(domaine);
            this.setPass1(pass1);
            this.setPass2(pass2);
        }
        else
            throw new Exception("Mot de passe non confirmé");
    }

    public Inscription(String nom, String prenom, String dateNaissance, String sexe, InfoAddress infoAddress, String pass1, String pass2) throws Exception {
        if(pass1.equals(pass2)) {
            this.setNom(nom);
            this.setPrenom(prenom);
            this.setDateNaissance(dateNaissance);
            this.setSexe(sexe);
            this.setInfoAddress(infoAddress);
            this.setPass1(pass1);
            this.setPass2(pass2);
        }
        else
            throw new Exception("Mot de passe non confirmé");
    }

    public Inscription() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getSociete() {
        return societe;
    }

    public void setSociete(String societe) {
        this.societe = societe;
    }

    public InfoAddress getInfoAddress() {
        return infoAddress;
    }

    public void setInfoAddress(InfoAddress infoAddress) {
        this.infoAddress = infoAddress;
    }

    public Domaine getDomaine() {
        return domaine;
    }

    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
    }

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }
}
