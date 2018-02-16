package com.lifestile.system.client.gestion;

import com.lifestile.system.client.Personne;

import java.io.Serializable;

public class Amis implements Serializable {
    private Personne personne1;
    private Personne personne2;

    public Amis(Personne personne1, Personne personne2) {
        setPersonne1(personne1);
        setPersonne2(personne2);
    }

    public Personne getPersonne1() {
        return personne1;
    }

    private void setPersonne1(Personne personne1) {
        this.personne1 = personne1;
    }

    public Personne getPersonne2() {
        return personne2;
    }

    private void setPersonne2(Personne personne2) {
        this.personne2 = personne2;
    }
}

