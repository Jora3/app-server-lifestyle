package services;

import com.api.ICategorie;
import com.lifestile.system.accessdb.catalogue.CategorieDAO;
import services.mapping.JsonResultats;

public class CategorieSE extends JsonResultats {
    private final ICategorie categorie = new CategorieDAO();

    public String categories() {
        return jsonFormat(false, "getAll");
    }

    public String inserer(String nom, String description) {
        return jsonFormat(true, "save", nom, description);
    }

    @Override
    protected Object object() {
        return categorie;
    }
}
