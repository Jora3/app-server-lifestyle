package com.api;

import com.lifestile.system.client.MIPublication;
import com.lifestile.system.client.MIStyles;
import com.lifestile.system.client.Personne;
import com.lifestile.system.client.PersonneInfoLogin;

import java.util.ArrayList;
@SuppressWarnings({"All"})
public interface IClientProfile {


    int nombreAmisEnCommun(String personneId1, String personneId2) throws Exception;

    void addAmis(String personneId1, String personneId2) throws Exception;

    void addInMystyles(String personneId, String publicationId, String confidence) throws Exception;

    void updateSecurityMIStyle(String personneId, String mistyleId, String confidence) throws Exception;

    void deleteSuggestion(String personneId, String suggestionId) throws Exception;

    boolean isAmi(String personneId1, String personneId2)throws Exception;

    boolean isAbonne(String personneId1, String personneId2) throws Exception;

    boolean isExistAccount(String addresse) throws Exception;

    Personne inscription(String nom, String prenom, String naissance, String sexe, String mail, String pass) throws Exception;

    PersonneInfoLogin login(String addresse, String password) throws Exception;

    ArrayList<Personne> amis(String personneId) throws Exception;

    ArrayList<Personne> amisASuggerer(String personneId, String publicationId) throws Exception;

    ArrayList<Personne> personnesSuggerer(String personneId) throws Exception;

    ArrayList<MIStyles> listMIStyles(String personneId) throws Exception;

    ArrayList<MIStyles> voirMIStyles(String personneId1, String personneId2) throws Exception;

    ArrayList<MIPublication> voirMIPublications(String personneId1, String personneId2)throws Exception;

}
