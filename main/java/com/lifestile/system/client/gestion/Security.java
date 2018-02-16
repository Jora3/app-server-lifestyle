package com.lifestile.system.client.gestion;

import java.io.Serializable;

public class Security implements Serializable {

    private String referenceId;
    private boolean publics = true;
    private boolean entreAmis;
    private boolean amisEtSesAmis;
    private boolean prive;

    public Security(String referenceId) throws Exception {
        setReferenceId(referenceId);
    }

    public Security() {}

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) throws Exception {
        if (referenceId == null) throw new Exception("Impossible d'associé une refenrence null à la securité");
        this.referenceId = referenceId;
    }

    public void prive() {
        this.publics = false;
        this.prive = true;
    }

    public void publics() {
        this.publics = true;
    }

    public void entreAmis() {
        this.publics = false;
        this.entreAmis = true;
    }

    public void amisEtSesAmis() {
        this.publics = false;
        this.amisEtSesAmis = true;
    }

    public boolean isPublics() {
        return publics;
    }

    public boolean isEntreAmis() {
        return entreAmis;
    }

    public boolean isAmisEtSesAmis() {
        return amisEtSesAmis;
    }

    public boolean isPrive() {
        return prive;
    }
}
