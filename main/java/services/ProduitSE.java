package services;

import com.api.IProduit;
import com.lifestile.system.accessdb.catalogue.ProduitDAO;
import services.mapping.JsonResultats;

public class ProduitSE extends JsonResultats {
    private final IProduit produit = new ProduitDAO();

    public String byId(String produitId) {
        return jsonFormat(false, "getById", produitId);
    }

    public String byEntreprise(String entrepriseId) {
        return jsonFormat(false, "getByEntreprise", entrepriseId);
    }

    public String inserer(String nom, String categorieId, String sousCategorieId, String typeId, String couleur, String tailleId, String prix, String entrepriseId) {
        return jsonFormat(false, "insert", nom, categorieId, sousCategorieId, typeId, couleur, tailleId, prix, entrepriseId);
    }

    @Override
    protected Object object() {
        return produit;
    }
}
