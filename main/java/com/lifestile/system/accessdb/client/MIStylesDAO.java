package com.lifestile.system.accessdb.client;

import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.client.MIStyles;
import com.lifestile.system.client.Personne;
import com.lifestile.system.client.gestion.Security;
import com.mongodb.MongoClient;

import java.util.ArrayList;

@SuppressWarnings({"All"})
public class MIStylesDAO extends ClassMapTable {
    private MIStyles miStyles;

    public MIStylesDAO(MIStyles miStyles) {
        setMIStyles(miStyles);
    }

    public MIStylesDAO() { }

    @Override
    public String toJson() {
        return gson.toJson(getMiStyles());
    }

    @Override
    public String nameTable() {
        return MIStyles.class.getSimpleName();
    }

    @Override
    public Class classMap() {
        return MIStyles.class;
    }

    @Override
    protected String referenceId() {
        return "MSTL";
    }

    public ArrayList<MIStylesDAO> miStyle(Personne personne, MongoClient client) throws Exception {
        return getAllIfContaints("personneId", personne.getPersonneId(), client);
    }

    void addStyle(MongoClient client) throws Exception {
        ArrayList<MIStylesDAO> styles = miStyle(this.getMiStyles().getStyles().getPersonne(), client);
        for(MIStylesDAO style : styles){
            if (style.getMiStyles().getStyles().getStyleId().equals(this.getMiStyles().getStyles().getStyleId())) {
                throw new Exception("Vous possedez déjà ce Style");
            }
        }
        this.getMiStyles().setMistylesId(id());
        super.save(client);
    }

    void updateSecurity(Security security, MongoClient client) throws Exception {
        this.getMiStyles().setSecurity(security);
        this.updateIfFound("mistylesId", this.getMiStyles().getMistylesId(), client);
    }

    MIStylesDAO getMiStyles(String mistyleId, Personne personne, MongoClient client) throws Exception {
        return (MIStylesDAO) getOneIfContaints("mistylesId", mistyleId, client);
    }

    public MIStyles getMiStyles() {
        return miStyles;
    }

    public void setMIStyles(MIStyles miStyles) {
        this.miStyles = miStyles;
    }
}
