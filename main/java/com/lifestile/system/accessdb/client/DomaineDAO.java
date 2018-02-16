package com.lifestile.system.accessdb.client;

import com.api.IDomaine;
import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.client.Domaine;
import com.mongodb.MongoClient;

import java.util.ArrayList;

public class DomaineDAO extends ClassMapTable implements IDomaine {
    private Domaine domaine;

    @Override
    public ArrayList<Domaine> getAll() throws Exception {
        MongoClient mongoClient = null;
        try {
            mongoClient = client();
            ArrayList domaineDAOS = list(mongoClient);
            ArrayList<Domaine> domaines = new ArrayList<>();
            for(Object o : domaineDAOS) {
                DomaineDAO domaineDAO = (DomaineDAO)o;
                domaines.add(domaineDAO.getDomaine());
            }
            return domaines;
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        finally {
            if(mongoClient != null)
                mongoClient.close();
        }
    }

    public Domaine getById(String domaineId, MongoClient mongoClient) throws Exception {
        return (Domaine)getOneIfExist("domaineId", domaineId, mongoClient);
    }

    public DomaineDAO(Domaine domaine) {
        this.setDomaine(domaine);
    }

    public DomaineDAO() {
    }

    public Domaine getDomaine() {
        return domaine;
    }

    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
    }

    @Override
    public String toJson() {
        return gson.toJson(domaine);
    }

    @Override
    public String nameTable() {
        return Domaine.class.getSimpleName();
    }

    @Override
    public Class classMap() {
        return Domaine.class;
    }

    @Override
    protected String referenceId() {
        return "DOMN";
    }
}
