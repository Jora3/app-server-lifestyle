package com.lifestile.system.accessdb.client.gestion;

import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.client.gestion.Security;
import com.mongodb.MongoClient;

public class SecurityDAO extends ClassMapTable {

    private Security security;

    public SecurityDAO(Security security) throws Exception {
        setSecurity(security);
    }

    public SecurityDAO() { }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) throws Exception {
        if (security.getReferenceId() == null) throw new Exception("La securité n'a pas de referenceId");
        this.security = security;
    }

    public SecurityDAO security(String securityId, MongoClient client) throws Exception {
        return (SecurityDAO) getOneIfContaints("referenceId", securityId, client);
    }

    public Security setSecurity(String typeSecurity) throws Exception{
        try {
            Security security = new Security();
            Security.class.getMethod(typeSecurity).invoke(security);
            return security;
        } catch (Exception e) {
            throw new Exception("Securité invalide");
        }
    }

    /**
     * Function @return a Json format.
     *
     * @return String
     */
    @Override
    public String toJson() {
        return gson.toJson(getSecurity());
    }

    /**
     * Function to specify a name of Collection mapping class.
     *
     * @return String
     */
    @Override
    public String nameTable() {
        return Security.class.getSimpleName();
    }

    /**
     * Function to specify a Class of object mapping data table.
     *
     * @return Class
     */
    @Override
    public Class classMap() {
        return Security.class;
    }

    @Override
    protected String referenceId() {
        return "SCUR";
    }
}
