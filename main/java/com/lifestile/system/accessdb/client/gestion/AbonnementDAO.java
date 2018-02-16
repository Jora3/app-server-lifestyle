package com.lifestile.system.accessdb.client.gestion;

import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.client.Personne;
import com.lifestile.system.client.gestion.Abonnement;
import com.mongodb.MongoClient;

import java.util.ArrayList;

public class AbonnementDAO extends ClassMapTable {
    private Abonnement abonnement;


    public AbonnementDAO(Abonnement abonnement) {
        this.setAbonnement(abonnement);
    }

    public AbonnementDAO() { }


    @Override
    public String toJson() {
        return gson.toJson(getAbonnement());
    }

    @Override
    public String nameTable() {
        return Abonnement.class.getSimpleName();
    }

    @Override
    public Class classMap() {
        return Abonnement.class;
    }

    @Override
    protected String referenceId() {
        return "ABON";
    }


    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    public ArrayList<AbonnementDAO> mesAbonnes(Personne personne, MongoClient client) throws Exception {
        String keys   = "personneId";
        String values = personne.getPersonneId();
        ArrayList<AbonnementDAO> list =  getAllIfContaints(keys, values, client);
        ArrayList<AbonnementDAO> listAbonnements = new ArrayList<>();
        for(AbonnementDAO abonnement : list){
            if (abonnement.getAbonnement().isAbonne()) {
                listAbonnements.add(abonnement);
            }
        }
        return listAbonnements;
    }

    public void doAbonnement(MongoClient client) throws Exception {
        String keys   = "personneId, utilisateurId";
        String values = getAbonnement().getPersonneId() + ", " + getAbonnement().getUtilisateurId();

        if (getOneIfContaints(keys, values, client) != null) {
            updateIfFounds(keys, values, client);
        } else {
            save(client);
        }
    }
}
