package com.api;

import com.lifestile.system.catalogue.Produit;
import com.lifestile.system.client.Entreprise;
import com.lifestile.util.TimeSpan;

import java.util.ArrayList;

public interface IProduit {
    Produit getById(String produitId) throws Exception;
    ArrayList<Produit> getByEntreprise(String entrepriseId) throws Exception;
    String insert(String nom, String categorieId, String sousCategorieId, String typeId, String couleur, String tailleId, String prix, String entrepriseId) throws Exception;
}
