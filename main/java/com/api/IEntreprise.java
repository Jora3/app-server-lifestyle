package com.api;

import com.lifestile.system.client.gestion.Publication;

import java.util.ArrayList;

public interface IEntreprise {
    ArrayList<Publication> publications(String entrepriseId) throws Exception;
    void publierProduit(String produitId, String entrepriseId) throws Exception;
}
