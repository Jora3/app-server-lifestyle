package com.lifestile.system.accessdb.catalogue;

import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.catalogue.Bazar;
import com.mongodb.MongoClient;

import java.util.ArrayList;

public class BazarDAO extends ClassMapTable {
    private Bazar bazar;

    public ArrayList<Bazar> getAll(MongoClient mongoClient) throws Exception {
        return list(mongoClient);
    }

    public Bazar insert(MongoClient mongoClient) throws Exception {
        bazar.setBazarId(id());
        if(bazar.getPhoto() == null)
            bazar.setPhoto(bazar.getBazarId());
        save(mongoClient);
        return bazar;
    }

    public BazarDAO(Bazar bazar) {
        this.setBazar(bazar);
    }

    public BazarDAO() {
    }

    public Bazar getBazar() {
        return bazar;
    }

    public void setBazar(Bazar bazar) {
        this.bazar = bazar;
    }

    @Override
    public String toJson() {
        return gson.toJson(bazar);
    }

    @Override
    public String nameTable() {
        return Bazar.class.getSimpleName();
    }

    @Override
    public Class classMap() {
        return Bazar.class;
    }

    @Override
    protected String referenceId() {
        return "BAZR";
    }
}
