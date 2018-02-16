package com.lifestile.system.client;

import com.lifestile.system.client.gestion.Publication;

public class MISuggestion {
    private String suggestionId;
    private Personne personne;
    private Publication publication;

    public MISuggestion(String suggestionId, Personne personne, Publication publication) {
        setSuggestionId(suggestionId);
        setPersonne(personne);
        setPublication(publication);
    }

    public MISuggestion(){}

    public void setSuggestionId(String suggestionId) {
        this.suggestionId = suggestionId;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public String getSuggestionId() {
        return suggestionId;
    }

    public Personne getPersonne() {
        return personne;
    }

    public Publication getPublication() {
        return publication;
    }
}
