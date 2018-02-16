package com.lifestile.system.accessdb.client.gestion;

import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.client.Personne;
import com.lifestile.system.client.gestion.Amis;
import com.mongodb.MongoClient;

import java.util.ArrayList;

@SuppressWarnings({"All"})
public class AmisDAO extends ClassMapTable {
    private Amis amis;

    public AmisDAO(Amis amis) {
        setAmis(amis);
    }

    public AmisDAO() {
    }

    public Amis getAmis() {
        return amis;
    }

    public void setAmis(Amis amis) {
        this.amis = amis;
    }

    /**
     * Function @return a Json format.
     *
     * @return String
     */
    @Override
    public String toJson() {
        return gson.toJson(amis);
    }

    /**
     * Function to specify a name of Collection mapping class.
     *
     * @return String
     */
    @Override
    public String nameTable() {
        return Amis.class.getSimpleName();
    }

    /**
     * Function to specify a Class of object mapping data table.
     *
     * @return Class
     */
    @Override
    public Class classMap() {
        return Amis.class;
    }

    @Override
    protected String referenceId() {
        return "AMIS";
    }

    public ArrayList<AmisDAO> getAllAmis(Personne personne, MongoClient client) throws Exception {
        ArrayList<AmisDAO> amis1 = getAllIfExist("personne1", gson.toJson(personne), client);
        ArrayList<AmisDAO> amis2 = getAllIfExist("personne2", gson.toJson(personne), client);
        amis1.addAll(amis2);
        return amis1;
    }


}
