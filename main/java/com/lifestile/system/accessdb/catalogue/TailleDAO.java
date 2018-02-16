package com.lifestile.system.accessdb.catalogue;

import com.api.ITaille;
import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.catalogue.Taille;
import com.mongodb.MongoClient;

import java.util.ArrayList;

public class TailleDAO extends ClassMapTable implements ITaille {
    private Taille taille;

    @Override
    public ArrayList<Taille> getAll() throws Exception {
        MongoClient mongoClient = null;
        try {
            mongoClient = client();
            ArrayList<Taille> tailles = new ArrayList<>();
            ArrayList<TailleDAO> tailleDAOS = list(mongoClient);
            for(TailleDAO tailleDAO : tailleDAOS)
                tailles.add(tailleDAO.getTaille());
            return tailles;
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        finally {
            if(mongoClient != null)
                mongoClient.close();
        }
    }

    @Override
    public void insert(String nom) throws Exception {
        MongoClient mongoClient = null;
        try {
            mongoClient = client();
            taille = new Taille(id(), nom);
            save(mongoClient);
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        finally {
            if(mongoClient != null)
                mongoClient.close();
        }
    }

    public Taille getById(String tailleId, MongoClient mongoClient) throws Exception {
        TailleDAO tailleDAO = (TailleDAO)getOneIfContaints("tailleId", tailleId, mongoClient);
        if(tailleDAO == null)
            return null;
        return tailleDAO.getTaille();
    }

    public TailleDAO(Taille taille) {
        this.setTaille(taille);
    }

    public TailleDAO() {
    }

    public Taille getTaille() {
        return taille;
    }

    public void setTaille(Taille taille) {
        this.taille = taille;
    }

    @Override
    public String toJson() {
        return gson.toJson(taille);
    }

    @Override
    public String nameTable() {
        return Taille.class.getSimpleName();
    }

    @Override
    public Class classMap() {
        return Taille.class;
    }

    @Override
    protected String referenceId() {
        return "TAIL";
    }
}
