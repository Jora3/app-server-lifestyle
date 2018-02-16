package com.lifestile.system.accessdb.client.gestion;

import com.api.ISuggestion;
import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.accessdb.catalogue.BazarDAO;
import com.lifestile.system.accessdb.client.MIStylesDAO;
import com.lifestile.system.accessdb.client.PersonneDAO;
import com.lifestile.system.catalogue.Bazar;
import com.lifestile.system.catalogue.Produit;
import com.lifestile.system.catalogue.Styles;
import com.lifestile.system.client.MIStyles;
import com.lifestile.system.client.MISuggestion;
import com.lifestile.system.client.Personne;
import com.lifestile.system.client.gestion.Publication;
import com.lifestile.system.client.gestion.Suggestion;
import com.mongodb.MongoClient;

import java.util.ArrayList;


public class SuggestionDAO extends ClassMapTable implements ISuggestion {

    private Suggestion suggestion;

    public SuggestionDAO(Suggestion suggestion) {
        setSuggestion(suggestion);
    }

    public SuggestionDAO() {
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    @Override
    public String toJson() {
        return gson.toJson(suggestion);
    }

    @Override
    public String nameTable() {
        return Suggestion.class.getSimpleName();
    }

    @Override
    public Class classMap() {
        return Suggestion.class;
    }

    @Override
    protected String referenceId() {
        return "SUGG";
    }

    @Override
    public void save(MongoClient client) throws Exception {
        suggestion.setSuggestionId(id());
        super.save(client);
    }

    public SuggestionDAO getSuggestion(String suggestionId, MongoClient client) throws Exception {
        return (SuggestionDAO) getOneIfContaints("suggestionId", suggestionId, client);
    }

    public ArrayList<SuggestionDAO> listSuggestions(Personne personne, MongoClient client) throws Exception {
        return getAllIfExist("personne", gson.toJson(personne), client);
    }

    ArrayList<MISuggestion> suggestions(PersonneDAO aPersonne, MongoClient client) throws Exception {
        ArrayList<MISuggestion>   mesSuggestions = new ArrayList<>();
        ArrayList<SuggestionDAO> suggestions    = list(client);
        for (SuggestionDAO suggestion : suggestions) {
            ArrayList<Personne> personnes = suggestion.getSuggestion().getPersonnes();
            for (Personne personne : personnes) {
                if (aPersonne.getUtilisateurId().equals(personne.getPersonneId())) {
                    mesSuggestions.add(new MISuggestion(
                            suggestion.getSuggestion().getSuggestionId(),
                            suggestion.getSuggestion().getPersonne(),
                            suggestion.getSuggestion().getPublication()
                    ));
                    break;
                }
            }
        }
        return mesSuggestions;
    }

    ArrayList<Suggestion> listSuggestions(Publication publication, MongoClient client) throws Exception {
        ArrayList<Suggestion> suggestions = listDocumentObject(client);
        ArrayList<Suggestion> suggerers = new ArrayList<>();
        for (Suggestion suggestion : suggestions) {
            if (suggestion.getPublication().equals(publication)) {
                suggerers.add(suggestion);
            }
        }
        return suggerers;
    }

    @Override
    public ArrayList<Bazar> suggestionsSysteme(String personne) throws Exception {
       return null;
    }
}
