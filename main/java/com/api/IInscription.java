package com.api;

public interface IInscription {
    Object sInscrire(String nom, String prenom, String dateNaissance, String sexe, String societe, String pays, String ville, String lieu, String mail, String mobile, String domaineId, String pass1, String pass2) throws Exception;
}
