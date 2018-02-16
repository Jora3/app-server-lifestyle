package com.lifestile.system.client.gestion;

import com.lifestile.system.client.Personne;
import com.lifestile.util.TimeSpan;

public class Partager {
    private String   partagerId;
    private String   datePartage;
    private Security security;
    private Personne personne;
    private Publication publication;

    public Partager(Security security, Personne personne, Publication publication) {
        setDatePartage(TimeSpan.now());
        setSecurity(security);
        setPersonne(personne);
        setPublication(publication);
    }

    public void setPartagerId(String partagerId) {
        this.partagerId = partagerId;
    }

    private void setDatePartage(String datePartage) {
        this.datePartage = datePartage;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public String getPartagerId() {
        return partagerId;
    }

    public String getDatePartage() {
        return datePartage;
    }

    public Security getSecurity() {
        return security;
    }

    public Personne getPersonne() {
        return personne;
    }

    public Publication getPublication() {
        return publication;
    }
}
