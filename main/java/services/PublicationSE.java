package services;

import com.api.IPublication;
import com.lifestile.system.accessdb.client.gestion.PublicationDAO;
import services.mapping.JsonResultats;


public class PublicationSE extends JsonResultats{

    private final IPublication publication = new PublicationDAO();

    public String byId(String publicationId) {
        return jsonFormat(false, "byId", publicationId);
    }

    public String mesPublications(String personneId) {
        return jsonFormat(false, "mesPublications", personneId);
    }

    public String publication(String personneId, String confidence, String styleId) {
        return jsonFormat(true, "publication", personneId, confidence, styleId);
    }

    public String doAction(String personneId, String publicationId, String action) {
        return jsonFormat(true, "doAction", personneId, publicationId, action);
    }

    public String reaction(String personneId, String publicationId) {
        return jsonFormat(false, "getReaction", personneId, publicationId);
    }

    public String commentaires(String publicationId) {
        return jsonFormat(false, "commentaires", publicationId);
    }

    public String doCommentaire(String personneId, String publicationId, String commentaire) {
        return jsonFormat(true, "doCommentaire", personneId, publicationId, commentaire);
    }

    public String doReponseCommetaire(String commentaireId, String personneId, String publicationId, String commentaire) {
        return jsonFormat(true, "doReponseCommetaire", commentaireId, personneId, publicationId, commentaire);
    }

    @Override
    protected Object object() {
        return publication;
    }
}
