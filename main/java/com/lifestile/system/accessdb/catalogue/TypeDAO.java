package com.lifestile.system.accessdb.catalogue;

import com.api.IType;
import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.catalogue.Type;
import com.mongodb.MongoClient;

import java.util.ArrayList;

public class TypeDAO extends ClassMapTable implements IType {
    private Type type;

    @Override
    public void insert(String nom, String description) throws Exception {
        MongoClient mongoClient = null;
        try {
            mongoClient = client();
            type = new Type(null, nom, description);
            type.setTypeId(id());
            save(mongoClient);
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        finally {
            if(mongoClient != null)
                mongoClient.close();
        }
    }

    @Override
    public ArrayList<Type> getAll() throws Exception {
        MongoClient mongoClient = null;
        try {
            mongoClient = client();
            ArrayList<Type> types = new ArrayList<>();
            ArrayList<TypeDAO> typeDAOS = list(client());
            for(TypeDAO typeDAO : typeDAOS)
                types.add(typeDAO.getType());
            return types;
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        finally {
            if(mongoClient != null)
                mongoClient.close();
        }
    }

    public Type getById(String typeId, MongoClient mongoClient) throws Exception {
        TypeDAO typeDAO = (TypeDAO)getOneIfContaints("typeId", typeId, mongoClient);
        if(typeDAO == null)
            return null;
        return typeDAO.getType();
    }

    public TypeDAO(Type type) {
        this.setType(type);
    }

    public TypeDAO() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toJson() {
        return gson.toJson(type);
    }

    @Override
    public String nameTable() {
        return Type.class.getSimpleName();
    }

    @Override
    public Class classMap() {
        return Type.class;
    }

    @Override
    protected String referenceId() {
        return "TYPE";
    }
}
