package com.lifestile.system.client;

import java.io.Serializable;

public class InfoAddress implements Serializable {
    private String infoAddresseId;
    private String pays;
    private String ville;
    private String lieu;
    private String mails;
    private String mobiles;

    public InfoAddress(String infoAddresseId, String pays, String ville, String lieu, String mails, String mobiles) {
        setInfoAddresseId(infoAddresseId);
        setPays(pays);
        setVille(ville);
        setLieu(lieu);
        setMails(mails);
        setMobiles(mobiles);
    }

    public String getInfoAddresseId() {
        return infoAddresseId;
    }

    public void setInfoAddresseId(String infoAddresseId) {
        this.infoAddresseId = infoAddresseId;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getMails() {
        return mails;
    }

    public void setMails(String mails) {
        this.mails = mails;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }
}
