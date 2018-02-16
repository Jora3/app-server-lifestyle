package com.lifestile.system.client.gestion;

import java.io.Serializable;

public class Abonnement implements Serializable {
    private boolean abonne;
    private String  personneId;
    private String  utilisateurId;

    public Abonnement(boolean abonne, String personneId, String utilisateurId) {
        this.setAbonne(abonne);
        this.setPersonneId(personneId);
        this.setUtilisateurId(utilisateurId);
    }

    public boolean isAbonne() {
        return abonne;
    }

    public void setAbonne(boolean abonne) {
        this.abonne = abonne;
    }

    public String getPersonneId() {
        return personneId;
    }

    public void setPersonneId(String personneId) {
        this.personneId = personneId;
    }

    public String getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(String personneId) {
        this.utilisateurId = personneId;
    }
}
