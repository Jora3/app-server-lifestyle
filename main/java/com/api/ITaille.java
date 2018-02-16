package com.api;

import com.lifestile.system.catalogue.Taille;

import java.util.ArrayList;

public interface ITaille {
    ArrayList<Taille> getAll() throws Exception;
    void insert(String nom) throws Exception;
}
