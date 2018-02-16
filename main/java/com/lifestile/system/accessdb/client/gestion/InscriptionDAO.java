package com.lifestile.system.accessdb.client.gestion;

import com.api.IInscription;
import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.accessdb.client.DomaineDAO;
import com.lifestile.system.accessdb.client.EntrepriseDAO;
import com.lifestile.system.accessdb.client.InfoAddressDAO;
import com.lifestile.system.client.Domaine;
import com.lifestile.system.client.Entreprise;
import com.lifestile.system.client.InfoAddress;
import com.lifestile.system.client.gestion.Inscription;
import com.mongodb.MongoClient;

import java.util.ArrayList;

public class InscriptionDAO extends ClassMapTable implements IInscription {
    @Override
    public Object sInscrire(String nom, String prenom, String dateNaissance, String sexe, String societe, String pays, String ville, String lieu, String mail, String mobile, String domaineId, String pass1, String pass2) throws Exception {
        MongoClient mongoClient = null;
        try {
            mongoClient = client();
            if(!dateNaissance.equals("nulle"))
                return inscriptionClient(nom, prenom, dateNaissance, sexe, pays, ville, lieu, mail, mobile, pass1, pass2, mongoClient);
            else
                return inscriptionEntreprise(societe, pays, ville, lieu, mail, mobile, domaineId, pass1, pass2, mongoClient);
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        finally {
            if(mongoClient != null)
                mongoClient.close();
        }
    }

    private Entreprise inscriptionEntreprise(String societe, String pays, String ville, String lieu, String mail, String mobile, String domaineId, String pass1, String pass2, MongoClient mongoClient) throws Exception {
        InfoAddress infoAddress = new InfoAddress(null, pays, ville, lieu, mail, mobile);
        InfoAddressDAO infoAddressDAO = new InfoAddressDAO(infoAddress);
        infoAddressDAO.save(mongoClient);
        Domaine domaine = new DomaineDAO().getById(domaineId, mongoClient);
        Inscription inscription = new Inscription(societe, infoAddress, domaine, pass1, pass2);
        Entreprise entreprise = new Entreprise(null, societe, new ArrayList<>(), domaine, pass1, true);
        entreprise.getInfoAddresses().add(infoAddress);
        return new EntrepriseDAO(entreprise).insert(mongoClient);
    }

    private Object inscriptionClient(String nom, String prenom, String dateNaissance, String sexe, String pays, String ville, String lieu, String mail, String mobile, String pass1, String pass2, MongoClient mongoClient) throws Exception {
        InfoAddress infoAddress = new InfoAddress(null, pays, ville, lieu, mail, mobile);
        InfoAddressDAO infoAddressDAO = new InfoAddressDAO(infoAddress);
        infoAddressDAO.save(mongoClient);
        Inscription inscription = new Inscription(nom, prenom, dateNaissance, sexe, infoAddress, pass1, pass2);
        return null;
    }

    public InscriptionDAO() {
    }

    @Override
    public String toJson() {
        return null;
    }

    @Override
    public String nameTable() {
        return null;
    }

    @Override
    public Class classMap() {
        return null;
    }

    @Override
    protected String referenceId() {
        return null;
    }
}
