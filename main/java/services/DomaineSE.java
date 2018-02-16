package services;

import com.api.IDomaine;
import com.lifestile.system.accessdb.client.DomaineDAO;
import services.mapping.JsonResultats;

public class DomaineSE extends JsonResultats {
    private final IDomaine domaine = new DomaineDAO();

    public String domaines() {
        return jsonFormat(false, "getAll");
    }

    @Override
    protected Object object() {
        return domaine;
    }
}
