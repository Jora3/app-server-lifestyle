package com.api;

import com.lifestile.system.catalogue.Type;

import java.util.ArrayList;

public interface IType {
    void insert(String nom, String description) throws Exception;
    ArrayList<Type> getAll() throws Exception;
}
