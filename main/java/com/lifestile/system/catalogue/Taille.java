package com.lifestile.system.catalogue;

public class Taille {
    private String tailleId;
    private String valeurTaille;

    public Taille(String tailleId, String valeurTaille) {
        this.setTailleId(tailleId);
        this.setValeurTaille(valeurTaille);
    }

    public Taille() {
    }

    public String getTailleId() {
        return tailleId;
    }

    public void setTailleId(String tailleId) {
        this.tailleId = tailleId;
    }

    public String getValeurTaille() {
        return valeurTaille;
    }

    public void setValeurTaille(String valeurTaille) {
        this.valeurTaille = valeurTaille;
    }
}
