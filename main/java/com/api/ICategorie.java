package com.api;

import com.lifestile.system.catalogue.Categorie;

import java.util.ArrayList;

public interface ICategorie {
    ArrayList<Categorie> getAll() throws Exception;
    void insert(String nom, String description) throws Exception;
}
