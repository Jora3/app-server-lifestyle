package services;

import com.api.ISousCategorie;
import com.lifestile.system.accessdb.catalogue.SousCategorieDAO;
import services.mapping.JsonResultats;

public class SousCategorieSE extends JsonResultats {
    private final ISousCategorie sousCategorie = new SousCategorieDAO();

    public String byCategorie(String categorieId) {
        return jsonFormat(false, "getByCategorieId", categorieId);
    }

    public String inserer(String categorieId, String nom, String description) {
        return jsonFormat(true, "save", categorieId, nom, description);
    }

    public String sousCategories() {
        return jsonFormat(false, "getAll");
    }

    @Override
    protected Object object() {
        return sousCategorie;
    }
}
