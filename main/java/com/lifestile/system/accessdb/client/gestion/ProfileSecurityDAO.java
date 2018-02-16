package com.lifestile.system.accessdb.client.gestion;

import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.client.Personne;
import com.lifestile.system.client.gestion.ProfileSecurity;
import com.lifestile.system.client.gestion.Security;
import com.mongodb.MongoClient;

public class ProfileSecurityDAO extends ClassMapTable {
    private ProfileSecurity profileSecurity;

    public ProfileSecurityDAO(Personne personne, Security security) {
        setProfileSecurity(new ProfileSecurity(personne, security));
    }

    public ProfileSecurityDAO() {
    }

    public ProfileSecurity getProfileSecurity() {
        return this.profileSecurity;
    }

    public void setProfileSecurity(ProfileSecurity profileSecurity) {
        this.profileSecurity = profileSecurity;
    }

    public ProfileSecurityDAO profileSecurity(Personne personne, MongoClient client) throws Exception {
        return (ProfileSecurityDAO) getOneIfContaints("personneId", personne.getPersonneId(), client);
    }

    @Override
    public String toJson() {
        return gson.toJson(getProfileSecurity());
    }

    @Override
    public String nameTable() {
        return ProfileSecurity.class.getSimpleName();
    }

    @Override
    public Class classMap() {
        return ProfileSecurity.class;
    }

    @Override
    protected String referenceId() {
        return "PSEC";
    }
}
