package com.lifestile.system.client.gestion;

import com.lifestile.system.catalogue.Bazar;
import com.lifestile.system.catalogue.Styles;

import java.io.Serializable;

public class Publication implements Serializable {
    private String   publicationId;
    private String   personneId;
    private String   date;

    private int      adorer;
    private int      aimer;
    private int      detester;
    private int      partager;
    private int      suggerer;
    private int      nbCommentaire;
    private int      nbSuggestion;

    private String   reaction = "";
    private Styles   styles;
    private Bazar bazar;
    private Security security;
    private Commentaire lastCommentaire = new Commentaire();

    public Publication(String date, Security security, Bazar bazar) {
        setDate(date);
        setSecurity(security);
        setBazar(bazar);
        setPersonneId(bazar.getEntreprise().getEntrepriseId());
    }

    public Publication(String date, Security security, Styles styles) {
        setDate(date);
        setStyles(styles);
        setSecurity(security);
        setPersonneId(getStyles().getPersonne().getPersonneId());
    }


    public Publication() { }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }

    public Styles getStyles() {
        return styles;
    }

    public void setStyles(Styles styles) {
        this.styles = styles;
    }

    public Bazar getBazar() {
        return bazar;
    }

    public void setBazar(Bazar bazar) {
        this.bazar = bazar;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public String getPersonneId() {
        return this.personneId;
    }

    public void setPersonneId(String personneId) {
        this.personneId = personneId;
    }

    public void setNbCommentaire(int nbCommentaire) {
        this.nbCommentaire = nbCommentaire;
    }

    public void setNbSuggestion(int nbSuggestion) {
        this.nbSuggestion = nbSuggestion;
    }

    public int getNbCommentaire() {
        return nbCommentaire;
    }

    public int getNbSuggestion() {
        return nbSuggestion;
    }

    public int getAdorer(){
        return adorer;
    }

    public void setAdorer(int adorer) {
        this.adorer += adorer;
    }

    public int getAimer() {
        return aimer;
    }

    public void setAimer(int aimer) {
        this.aimer += aimer;
    }

    public int getDetester() {
        return detester;
    }

    public void setDetester(int detester) {
        this.detester += detester;
    }

    public int getPartager() {
        return partager;
    }

    public void setPartager(int partager) {
        this.partager += partager;
    }

    public int getSuggerer() {
        return suggerer;
    }

    public void setSuggerer(int suggerer) {
        this.suggerer += suggerer;
    }

    public void setReaction(String reaction){
        this.reaction = reaction;
    }

    public String getReaction(){
        return reaction;
    }

    public void setLastCommentaire(Commentaire lastCommentaire) {
        this.lastCommentaire = lastCommentaire;
    }

    public Commentaire getLastCommentaire() {
        return this.lastCommentaire;
    }

    public boolean equals(Object o) {
        return this.getPublicationId().equals(((Publication) o).getPublicationId());
    }
}
