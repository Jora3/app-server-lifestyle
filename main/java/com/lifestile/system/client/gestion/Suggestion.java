package com.lifestile.system.client.gestion;

import com.lifestile.system.client.Personne;

import java.io.Serializable;
import java.util.ArrayList;

public class Suggestion implements Serializable {
    private String              suggestionId;
    private Personne            personne;
    private Publication         publication;
    private ArrayList<Personne> personnes;

    public Suggestion(Personne personne, Publication publication, ArrayList<Personne> personnes) {
        setPersonne(personne);
        setPublication(publication);
        setPersonnes(personnes);
    }

    public String getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(String suggestionId) {
        this.suggestionId = suggestionId;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public ArrayList<Personne> getPersonnes() {
        return personnes;
    }

    public void setPersonnes(ArrayList<Personne> personnes) {
        this.personnes = personnes;
    }
}
