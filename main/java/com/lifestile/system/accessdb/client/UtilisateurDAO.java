package com.lifestile.system.accessdb.client;

import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.accessdb.client.gestion.ProfileSecurityDAO;
import com.lifestile.system.client.gestion.Security;
import com.mongodb.MongoClient;


public abstract class UtilisateurDAO extends ClassMapTable {

    public abstract String getNomComplet();

    public abstract String getUtilisateurId();

    public abstract ProfileSecurityDAO profileSecurity(MongoClient client) throws Exception;

    public abstract void updateProfileSecurity(Security security, MongoClient client) throws Exception;

}
