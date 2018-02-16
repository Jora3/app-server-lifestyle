package com.lifestile.system.accessdb.catalogue;

import com.api.ICategorie;
import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.catalogue.Categorie;
import com.mongodb.MongoClient;

import java.util.ArrayList;

public class CategorieDAO extends ClassMapTable implements ICategorie {
    private Categorie categorie;

    @Override
    public ArrayList<Categorie> getAll() throws Exception {
        MongoClient mongoClient = null;
        try {
            mongoClient = client();
            ArrayList<Categorie> categories = new ArrayList<>();
            ArrayList<CategorieDAO> categorieDAOS = list(mongoClient);
            for(CategorieDAO categorieDAO : categorieDAOS)
                categories.add(categorieDAO.getCategorie());
            return categories;
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
    public void insert(String nom, String description) throws Exception {
        MongoClient mongoClient = null;
        try {
            mongoClient = client();
            categorie = new Categorie(null, nom, description);
            categorie.setCategorieId(id());
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

    public Categorie getById(String categorieId, MongoClient mongoClient) throws Exception {
        CategorieDAO categorieDAO = (CategorieDAO)getOneIfContaints("categorieId", categorieId, mongoClient);
        if(categorieDAO == null)
            return null;
        return categorieDAO.getCategorie();
    }

    public CategorieDAO(Categorie categorie) {
        this.setCategorie(categorie);
    }

    public CategorieDAO() {
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toJson() {
        return gson.toJson(categorie);
    }

    @Override
    public String nameTable() {
        return Categorie.class.getSimpleName();
    }

    @Override
    public Class classMap() {
        return Categorie.class;
    }

    @Override
    protected String referenceId() {
        return "CATE";
    }
}
