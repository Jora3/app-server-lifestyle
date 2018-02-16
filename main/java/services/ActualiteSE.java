package services;

import com.api.IActualite;
import com.lifestile.system.accessdb.client.gestion.ActualiteDAO;
import services.mapping.JsonResultats;

public class ActualiteSE extends JsonResultats{

    private final IActualite actualite = new ActualiteDAO();

    public String filtrer(String personneId, String type) {
        return jsonFormat(true, "filtrer", personneId, type);
    }

    public String suggerer(String personneId1, String publicationId, String personneId2) {
        return jsonFormat(true, "suggerer", personneId1, publicationId, personneId2);
    }

    public String nouvelles(String personneId){
        return jsonFormat(false, "nouvelles", personneId);
    }

    public String annulerSuggestion(String personneId1, String publicationId, String personneId2){
        return jsonFormat(true, "annulerSuggestion", personneId1, publicationId, personneId2);
    }

    public String partager(String securite, String personneId, String publicationId) {
        return jsonFormat(true, "partager", securite, personneId, publicationId);
    }

    public String actualites(String personneId) {
        return jsonFormat(false, "mesActualites", personneId);
    }

    public String listSuggerer(String publicationId){
        return jsonFormat(false, "listSuggerer", publicationId);
    }

    public String suggestions(String personneId) {
        return jsonFormat(false, "mesSuggestions", personneId);
    }

    @Override
    protected Object object() {
        return actualite;
    }
}
