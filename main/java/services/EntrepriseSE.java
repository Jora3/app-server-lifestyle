package services;

import com.api.IEntreprise;
import com.lifestile.system.accessdb.client.EntrepriseDAO;
import services.mapping.JsonResultats;

public class EntrepriseSE extends JsonResultats {
    private final IEntreprise entreprise = new EntrepriseDAO();

    public String publications(String entrepriseId) {
        return jsonFormat(false, "publications", entrepriseId);
    }

    public String publierProduit(String produitId, String entrepriseId) {
        return jsonFormat(true, "publierProduit", produitId, entrepriseId);
    }

    @Override
    protected Object object() {
        return entreprise;
    }
}
