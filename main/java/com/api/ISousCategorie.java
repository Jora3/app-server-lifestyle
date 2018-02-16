package com.api;

import com.lifestile.system.catalogue.SousCategorie;

import java.util.ArrayList;

public interface ISousCategorie {
    ArrayList<SousCategorie> getByCategorieId(String categorieId) throws Exception;
    void insert(String categorieId, String nom, String description) throws Exception;
    ArrayList<SousCategorie> getAll() throws Exception;
}
