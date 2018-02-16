package com.lifestile.system.accessdb.client.gestion;

import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.client.Personne;
import com.lifestile.system.client.gestion.Partager;
import com.lifestile.system.client.gestion.Publication;
import com.lifestile.system.client.gestion.Security;
import com.mongodb.MongoClient;

public class PartagerDAO extends ClassMapTable 
{
    private Partager partager;

    PartagerDAO(Partager partager) {
        setPartager(partager);
    }

    public PartagerDAO(){}

    void partager(Security security, Personne personne, Publication publication, MongoClient client) throws Exception {
        setPartager(new Partager(security, personne, publication));
        save(client);
    }

    @Override
    public String toJson() {
        return gson.toJson(getPartager());
    }

    @Override
    public String nameTable() {
        return Partager.class.getSimpleName();
    }

    @Override
    public Class classMap() {
        return Partager.class;
    }

    @Override
    protected String referenceId() {
        return "PRTG";
    }

    @Override
    public void save(MongoClient client) throws Exception {
        partager.setPartagerId(id());
        super.save(client);
    }

    public void setPartager(Partager partager) {
        this.partager = partager;
    }

    public Partager getPartager() {
        return partager;
    }
}
