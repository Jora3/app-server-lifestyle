package com.lifestile.system.accessdb.catalogue;

import com.api.ISousCategorie;
import com.google.gson.Gson;
import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.catalogue.Categorie;
import com.lifestile.system.catalogue.SousCategorie;
import com.mongodb.MongoClient;

import java.util.ArrayList;

public class SousCategorieDAO extends ClassMapTable implements ISousCategorie {
    private SousCategorie sousCategorie;

    @Override
    public ArrayList<SousCategorie> getByCategorieId(String categorieId) throws Exception {
        MongoClient mongoClient = null;
        try {
            mongoClient = client();
            CategorieDAO categorieDAO = new CategorieDAO();
            Categorie categorie = categorieDAO.getById(categorieId, mongoClient);
            ArrayList<SousCategorie> sousCategories = new ArrayList<>();
            ArrayList<SousCategorieDAO> sousCategorieDAOS = (ArrayList<SousCategorieDAO>)getAllIfExist("categorie", new Gson().toJson(categorie), mongoClient);
            for(SousCategorieDAO sousCategorieDAO : sousCategorieDAOS)
                sousCategories.add(sousCategorieDAO.getSousCategorie());
            return sousCategories;
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
    public void insert(String categorieId, String nom, String description) throws Exception {
        MongoClient mongoClient = null;
        try {
            mongoClient = client();
            Categorie categorie = new CategorieDAO().getById(categorieId, mongoClient);
            sousCategorie = new SousCategorie(id(), nom, categorie, description);
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

    @Override
    public ArrayList<SousCategorie> getAll() throws Exception {
        MongoClient mongoClient = null;
        try {
            mongoClient = client();
            ArrayList<SousCategorie> sousCategories = new ArrayList<>();
            ArrayList<SousCategorieDAO> sousCategorieDAOS = list(mongoClient);
            for(SousCategorieDAO sousCategorieDAO : sousCategorieDAOS)
                sousCategories.add(sousCategorieDAO.getSousCategorie());
            return sousCategories;
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        finally {
            if(mongoClient != null)
                mongoClient.close();
        }
    }

    public SousCategorie getById(String sousCategorieId, MongoClient mongoClient) throws Exception {
        SousCategorieDAO sousCategorieDAO = (SousCategorieDAO)getOneIfContaints("sousCategorieId", sousCategorieId, mongoClient);
        if(sousCategorieDAO == null)
            return null;
        return sousCategorieDAO.getSousCategorie();
    }

    public SousCategorieDAO(SousCategorie sousCategorie) {
        this.setSousCategorie(sousCategorie);
    }

    public SousCategorieDAO() {
    }

    public SousCategorie getSousCategorie() {
        return sousCategorie;
    }

    public void setSousCategorie(SousCategorie sousCategorie) {
        this.sousCategorie = sousCategorie;
    }

    @Override
    public String toJson() {
        return gson.toJson(sousCategorie);
    }

    @Override
    public String nameTable() {
        return SousCategorie.class.getSimpleName();
    }

    @Override
    public Class classMap() {
        return SousCategorie.class;
    }

    @Override
    protected String referenceId() {
        return "SCAT";
    }
}
