package com.lifestile.system.accessdb.client;

import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.client.InfoAddress;
import com.mongodb.MongoClient;

import javax.persistence.criteria.CriteriaBuilder;

public class InfoAddressDAO extends ClassMapTable {
    private InfoAddress infoAddress;

    public void save(MongoClient mongoClient) throws Exception {
        infoAddress.setInfoAddresseId(id());
        super.save(mongoClient);
    }

    public InfoAddress getByEmail(String email, MongoClient mongoClient) throws Exception {
        InfoAddressDAO infoAddressDAO = (InfoAddressDAO) getOneIfContaints("mails", email, mongoClient);
        if(infoAddressDAO == null)
            return null;
        return infoAddressDAO.infoAddress;
    }

    public InfoAddressDAO(InfoAddress infoAddress) {
        this.setInfoAddress(infoAddress);
    }

    public InfoAddressDAO() {
    }

    public InfoAddress getInfoAddress() {
        return infoAddress;
    }

    public void setInfoAddress(InfoAddress infoAddress) {
        this.infoAddress = infoAddress;
    }

    @Override
    public String toJson() {
        return gson.toJson(infoAddress);
    }

    @Override
    public String nameTable() {
        return InfoAddress.class.getSimpleName();
    }

    @Override
    public Class classMap() {
        return InfoAddress.class;
    }

    @Override
    protected String referenceId() {
        return "ADDR";
    }
}
