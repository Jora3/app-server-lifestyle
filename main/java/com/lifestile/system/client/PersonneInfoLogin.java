package com.lifestile.system.client;

@SuppressWarnings({"All"})
public class PersonneInfoLogin {
    private String personneId;
    private String nomComplet;
    private boolean userValid;
    private boolean passValid;
    private Personne personne;
    private String information;
    private int nombreAmis;
    private int nombreAbonnes;

    public PersonneInfoLogin(String personneId, String nomComplet, boolean userValid, boolean passValid, Personne personne, int nombreAmis, int nombreAbonnes) {
        this.personneId = personneId;
        this.nomComplet = nomComplet;
        this.userValid = userValid;
        this.passValid = passValid;
        this.personne = personne;
        this.nombreAmis = nombreAmis;
        this.nombreAbonnes = nombreAbonnes;
    }

    public void setPersonneId(String personneId) {
        this.personneId = personneId;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public boolean isUserValid() {
        return userValid;
    }

    public boolean isPassValid() {
        return passValid;
    }

    public void setUserValid(boolean userValid) {
        this.userValid = userValid;
    }

    public void setPassValid(boolean passValid) {
        this.passValid = passValid;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPersonneId() {
        return personneId;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public Personne getPersonne() {
        return personne;
    }

    public int getNombreAmis() {
        return nombreAmis;
    }

    public void setNombreAmis(int nombreAmis) {
        this.nombreAmis = nombreAmis;
    }

    public int getNombreAbonnes() {
        return nombreAbonnes;
    }

    public void setNombreAbonnes(int nombreAbonnes) {
        this.nombreAbonnes = nombreAbonnes;
    }
}
