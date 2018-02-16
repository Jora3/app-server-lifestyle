package com.lifestile.system.accessdb.catalogue;

import com.api.IProduit;
import com.google.gson.Gson;
import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.accessdb.client.EntrepriseDAO;
import com.lifestile.system.catalogue.*;
import com.lifestile.system.client.Entreprise;
import com.lifestile.util.TimeSpan;
import com.mongodb.MongoClient;

import java.util.ArrayList;

public class ProduitDAO extends ClassMapTable implements IProduit {
    private Produit produit;

    public ArrayList<Produit> getByIds(ArrayList<String> ids, MongoClient mongoClient) throws Exception {
        ArrayList<Produit> produits = new ArrayList<>();
        for(String id : ids) {
            ProduitDAO produitDAO = (ProduitDAO) getOneIfContaints("produitId", id, mongoClient);
            if(produitDAO != null)
                produits.add(produitDAO.getProduit());
        }
        return  produits;
    }

    @Override
    public Produit getById(String produitId) throws Exception {
        MongoClient mongoClient = null;
        try {
            mongoClient = client();
            return getById(produitId, mongoClient);
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
    public ArrayList<Produit> getByEntreprise(String entrepriseId) throws Exception {
        MongoClient mongoClient = null;
        try {
            mongoClient = client();
            EntrepriseDAO entrepriseDAO = new EntrepriseDAO();
            Entreprise entreprise = entrepriseDAO.getById(entrepriseId, mongoClient);
            ArrayList<ProduitDAO> produitDAOS = (ArrayList<ProduitDAO>) getAllIfExist("proprietaire", new Gson().toJson(entreprise), mongoClient);
            ArrayList<Produit> produits = new ArrayList<>();
            for(ProduitDAO produitDAO : produitDAOS)
                produits.add(produitDAO.getProduit());
            return produits;
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
    public String insert(String nom, String categorieId, String sousCategorieId, String typeId, String couleur, String tailleId, String prix, String entrepriseId) throws Exception {
        MongoClient mongoClient = null;
        try {
            mongoClient = client();
            CategorieDAO categorieDAO = new CategorieDAO();
            Categorie categorie = categorieDAO.getById(categorieId, mongoClient);
            SousCategorieDAO sousCategorieDAO = new SousCategorieDAO();
            SousCategorie sousCategorie = sousCategorieDAO.getById(sousCategorieId, mongoClient);
            TypeDAO typeDAO = new TypeDAO();
            Type type = typeDAO.getById(typeId, mongoClient);
            TailleDAO tailleDAO = new TailleDAO();
            Taille taille = tailleDAO.getById(tailleId, mongoClient);
            Details details = new Details(couleur, taille);
            EntrepriseDAO entrepriseDAO = new EntrepriseDAO();
            Entreprise entreprise = entrepriseDAO.getById(entrepriseId, mongoClient);
            String produitId = id();
            setProduit(new Produit(produitId, nom, categorie, sousCategorie, type, details, new Double(prix), produitId, TimeSpan.now(), entreprise));
            save(mongoClient);
            return produit.getProduitId();
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        finally {
            if(mongoClient != null)
                mongoClient.close();
        }
    }

    public ProduitDAO(Produit produit) {
        this.setProduit(produit);
    }

    public ProduitDAO() {
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public String toJson() {
        return gson.toJson(produit);
    }

    @Override
    public String nameTable() {
        return Produit.class.getSimpleName();
    }

    @Override
    public Class classMap() {
        return Produit.class;
    }

    @Override
    protected String referenceId() {
        return "PROD";
    }

    public Produit getById(String produitId, MongoClient mongoClient) throws Exception {
        ProduitDAO produitDAO = (ProduitDAO)getOneIfContaints("produitId", produitId, mongoClient);
        if(produitDAO != null)
            return  produitDAO.getProduit();
        return null;
    }
}
