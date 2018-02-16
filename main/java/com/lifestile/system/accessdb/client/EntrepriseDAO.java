package com.lifestile.system.accessdb.client;

import com.api.IEntreprise;
import com.google.gson.Gson;
import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.accessdb.catalogue.BazarDAO;
import com.lifestile.system.accessdb.catalogue.ProduitDAO;
import com.lifestile.system.accessdb.catalogue.StylesDAO;
import com.lifestile.system.accessdb.client.gestion.ProfileSecurityDAO;
import com.lifestile.system.accessdb.client.gestion.PublicationDAO;
import com.lifestile.system.accessdb.client.gestion.SecurityDAO;
import com.lifestile.system.catalogue.Bazar;
import com.lifestile.system.catalogue.Produit;
import com.lifestile.system.catalogue.Styles;
import com.lifestile.system.client.Entreprise;
import com.lifestile.system.client.InfoAddress;
import com.lifestile.system.client.gestion.Publication;
import com.lifestile.system.client.gestion.Security;
import com.lifestile.util.TimeSpan;
import com.mongodb.MongoClient;

import java.util.ArrayList;

public class EntrepriseDAO extends UtilisateurDAO implements IEntreprise {
    private Entreprise entreprise;

    @Override
    public ArrayList<Publication> publications(String entrepriseId) throws Exception {
        MongoClient mongoClient = null;
        try {
            mongoClient = client();
            return new PublicationDAO().getByEntreprise(entrepriseId, mongoClient);
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
    public void publierProduit(String produitId, String entrepriseId) throws Exception {
        MongoClient mongoClient = null;
        try {
            mongoClient = client();
            Produit produit = new ProduitDAO().getById(produitId, mongoClient);
            Entreprise entreprise = new EntrepriseDAO().getById(entrepriseId, mongoClient);
            Security security = new Security(entrepriseId);
            SecurityDAO securityDAO = new SecurityDAO(security);
            securityDAO.save(mongoClient);
            Bazar bazar = new Bazar(null, produit, produit.getDescription(), produit.getPhoto(), entreprise);
            System.out.println("produit : " + bazar.getProduit());
            BazarDAO bazarDAO = new BazarDAO(bazar);
            bazarDAO.insert(mongoClient);
            Publication publication = new Publication(TimeSpan.now(), security, bazar);
            PublicationDAO publicationDAO = new PublicationDAO(publication);
            publicationDAO.save(mongoClient);
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        finally {
            if(mongoClient != null)
                mongoClient.close();
        }
    }

    public Entreprise getByInfoAddress(InfoAddress infoAddress, MongoClient mongoClient) throws Exception {
        ArrayList<EntrepriseDAO> entrepriseDAOS = (ArrayList<EntrepriseDAO>) getAllIfExist("etat", new Gson().toJson(true), mongoClient);
        for(EntrepriseDAO entrepriseDAO : entrepriseDAOS) {
            Entreprise entreprise = entrepriseDAO.getEntreprise();
            ArrayList<InfoAddress> infoAddresses = entreprise.getInfoAddresses();
            for(InfoAddress infoAddress1 : infoAddresses) {
                if(infoAddress.getInfoAddresseId().equals(infoAddress1.getInfoAddresseId()))
                    return entreprise;
            }
        }
        return null;
    }

    public Entreprise getById(String entrepriseId, MongoClient mongoClient) throws Exception {
        EntrepriseDAO entrepriseDAO = (EntrepriseDAO) getOneIfContaints("entrepriseId", entrepriseId, mongoClient);
        if(entrepriseDAO == null)
            return null;
        return entrepriseDAO.getEntreprise();
    }

    public Entreprise insert(MongoClient mongoClient) throws Exception {
        entreprise.setEntrepriseId(id());
        save(mongoClient);
        return entreprise;
    }

    public EntrepriseDAO(Entreprise entreprise) {
        this.setEntreprise(entreprise);
    }

    public EntrepriseDAO() {
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    @Override
    public String toJson() {
        return gson.toJson(entreprise);
    }

    @Override
    public String nameTable() {
        return Entreprise.class.getSimpleName();
    }

    @Override
    public Class classMap() {
        return Entreprise.class;
    }

    @Override
    protected String referenceId() {
        return "ENTR";
    }

    @Override
    public String getNomComplet() {
        return entreprise.getNomEntreprise();
    }

    @Override
    public String getUtilisateurId() {
        return entreprise.getEntrepriseId();
    }

    @Override
    public ProfileSecurityDAO profileSecurity(MongoClient client) throws Exception {
        return null;
    }

    @Override
    public void updateProfileSecurity(Security security, MongoClient client) throws Exception {

    }
}
