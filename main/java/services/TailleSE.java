package services;

import com.api.ITaille;
import com.lifestile.system.accessdb.catalogue.TailleDAO;
import services.mapping.JsonResultats;

public class TailleSE extends JsonResultats {
    private final ITaille taille = new TailleDAO();

    public String tailles() {
        return jsonFormat(false, "getAll");
    }

    public String inserer(String nom) {
        return jsonFormat(true, "save", nom);
    }

    @Override
    protected Object object() {
        return taille;
    }
}
