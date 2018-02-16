package com.lifestile.system.client;

import java.io.Serializable;

public class Personne implements Serializable {
    private String      personneId;
    private String      nom;
    private String      prenom;
    private String      dateNaissance;
    private String      sexe;
    private String      password;
    private InfoAddress infoAddress;

    public Personne(String personneId, String nom, String prenom, String dateNaissance, String sexe, String pass, InfoAddress infoAddress) throws Exception {
        setPersonneId(personneId);
        setNom(nom);
        setPrenom(prenom);
        setDateNaissance(dateNaissance);
        setSexe(sexe);
        setPassword(pass);
        setInfoAddress(infoAddress);
    }

    public Personne() { }

    public String getPersonneId() {
        return personneId;
    }

    public void setPersonneId(String personneId) {
        this.personneId = personneId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws Exception {
        if (nom == null) throw new Exception("Votre nom semble vide.");
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) throws Exception {
        if (prenom == null) throw new Exception("Votre prenom Semble vide.");
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

    public void setSexe(String sexe) throws Exception {
        if (sexe == null) throw new Exception("Precisez votre sexe");
        this.sexe = sexe;
    }

    public InfoAddress getInfoAddress() {
        return infoAddress;
    }

    public void setInfoAddress(InfoAddress infoAddress) {
        this.infoAddress = infoAddress;
    }

    public boolean equals(Object o) {
        return this.personneId.equals(((Personne) o).getPersonneId());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
