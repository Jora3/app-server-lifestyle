package com.api;

import com.lifestile.system.client.MISuggestion;
import com.lifestile.system.client.gestion.Partager;
import com.lifestile.system.client.gestion.Publication;
import com.lifestile.system.client.gestion.Suggestion;

import java.util.ArrayList;

public interface IActualite {

    void filtrer(String personeId, String method) throws Exception;

    void suggerer(String personneId1, String publicationId, String personneId2) throws Exception;

    void annulerSuggestion(String personneId1, String publicationId, String personneId2) throws Exception;

    void partager(String securiter, String personneId, String publicationId) throws Exception;

    ArrayList<Publication> nouvelles(String personneId) throws Exception;

    ArrayList<Suggestion> listSuggerer(String publicatoinId) throws Exception;

    ArrayList<MISuggestion> mesSuggestions(String personneId) throws Exception;

    ArrayList<Partager> mesActualites(String personneId) throws Exception;

}

