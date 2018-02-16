package com.lifestile.system.client.gestion;

import com.lifestile.system.client.Personne;

import java.io.Serializable;

public class ProfileSecurity implements Serializable {
    private String   personneId;
    private Security security;

    public ProfileSecurity(Personne personne, Security security) {
        setPersonneId(personne.getPersonneId());
        setSecurity(security);
    }


    public String getPersonneId() {
        return personneId;
    }

    public void setPersonneId(String personneId) {
        this.personneId = personneId;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }
}
