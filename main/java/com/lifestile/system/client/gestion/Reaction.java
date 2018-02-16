package com.lifestile.system.client.gestion;

import java.io.Serializable;

public class Reaction implements Serializable {
    private String  publicationId;
    private String  personneId;

    private boolean adorer;
    private boolean aimer;
    private boolean detester;

    private boolean partager;
    private boolean suggerer;


    public Reaction(String publicationId, String personnneId) {
        setPublicationId(publicationId);
        setPersonneId(personnneId);
    }

    public Reaction() { }

    public boolean isAdorer(){
        return adorer;
    }

    public void setAdorer(boolean adorer) {
        this.adorer = adorer;
    }

    public boolean isAimer() {
        return aimer;
    }

    public void setAimer(boolean aimer) {
        this.aimer = aimer;
    }

    public boolean isDetester() {
        return detester;
    }

    public void setDetester(boolean detester) {
        this.detester = detester;
    }

    public boolean isPartager() {
        return partager;
    }

    public void setPartager(boolean partager) {
        this.partager = partager;
    }

    public boolean isSuggerer() {
        return suggerer;
    }

    public void setSuggerer(boolean suggerer) {
        this.suggerer = suggerer;
    }

    public String getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }

    public String getPersonneId() {
        return personneId;
    }

    public void setPersonneId(String personneId) {
        this.personneId = personneId;
    }
}
