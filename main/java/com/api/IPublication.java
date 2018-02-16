package com.api;

import com.lifestile.system.client.gestion.Commentaire;
import com.lifestile.system.client.gestion.Publication;
import com.lifestile.system.client.gestion.Reaction;

import java.util.ArrayList;

public interface IPublication {

    Publication byId(String publicationId) throws Exception;

    ArrayList<Publication> mesPublications(String personneId) throws Exception;

    ArrayList<Commentaire> commentaires(String publicaionId) throws Exception;

    void publication(String personneId, String confidence, String styleId) throws Exception;

    void doAction(String personneId, String publicationId, String action) throws Exception;

    void doCommentaire(String personneId, String publicationId, String commentaire) throws Exception;

    void doReponseCommentaire(String commentaireId, String personneId, String publicationId, String commentaire) throws Exception;
}
