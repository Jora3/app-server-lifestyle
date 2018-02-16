package services;

import com.api.IType;
import com.lifestile.system.accessdb.catalogue.TypeDAO;
import services.mapping.JsonResultats;

public class TypeSE extends JsonResultats {
    private final IType type = new TypeDAO();

    public String inserer(String nom, String description) {
        return jsonFormat(true, "save", nom, description);
    }

    public String types() {
        return jsonFormat(false, "getAll");
    }

    @Override
    protected Object object() {
        return type;
    }
}
