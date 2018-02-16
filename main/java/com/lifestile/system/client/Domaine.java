package com.lifestile.system.client;

import java.io.Serializable;

public class Domaine implements Serializable {
    private String domaineId;
    private String nomDomaine;

    public Domaine(String domaineId, String nomDomaine) {
        this.setDomaineId(domaineId);
        this.setNomDomaine(nomDomaine);
    }

    public Domaine() {
    }

    public String getDomaineId() {
        return domaineId;
    }

    public void setDomaineId(String domaineId) {
        this.domaineId = domaineId;
    }

    public String getNomDomaine() {
        return nomDomaine;
    }

    public void setNomDomaine(String nomDomaine) {
        this.nomDomaine = nomDomaine;
    }
}
