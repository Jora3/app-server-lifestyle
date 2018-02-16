package com.lifestile.system.accessdb.client.gestion;

import com.api.ILogin;
import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.accessdb.client.EntrepriseDAO;
import com.lifestile.system.accessdb.client.InfoAddressDAO;
import com.lifestile.system.client.Entreprise;
import com.lifestile.system.client.InfoAddress;
import com.lifestile.system.client.gestion.Login;
import com.mongodb.MongoClient;

public class LoginDAO extends ClassMapTable implements ILogin {
    private Login login;

    @Override
    public Object login(String email, String password) throws Exception {
        MongoClient mongoClient = null;
        try {
            login = new Login(email, password);
            mongoClient = client();
            InfoAddress infoAddress = new InfoAddressDAO().getByEmail(login.getEmail(), mongoClient);
            if(infoAddress == null)
                throw new Exception("Adresse email introuvable " + email);
            Entreprise entreprise = new EntrepriseDAO().getByInfoAddress(infoAddress, mongoClient);
            if(entreprise != null) {
                if(entreprise.getPass().equals(login.getPassword()))
                    return  entreprise;
                else
                    throw new Exception("Votre mot de passe est incorrect");
            }
            return null;
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        finally {
            if(mongoClient != null)
                mongoClient.close();
        }
    }

    public LoginDAO(Login login) {
        this.login = login;
    }

    public LoginDAO() {
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
