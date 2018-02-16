package com.lifestile.system.accessdb.client.gestion;

import com.api.IPublication;
import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.accessdb.catalogue.StylesDAO;
import com.lifestile.system.accessdb.client.PersonneDAO;
import com.lifestile.system.client.gestion.Commentaire;
import com.lifestile.system.client.gestion.Publication;
import com.lifestile.system.client.gestion.Reaction;
import com.lifestile.system.client.gestion.Security;
import com.lifestile.util.TimeSpan;
import com.mongodb.MongoClient;

import java.util.ArrayList;

@SuppressWarnings({"All"})
public class PublicationDAO extends ClassMapTable implements IPublication {
    private Publication publication;

    public ArrayList<Publication> getByEntreprise(String entrepriseId, MongoClient mongoClient) throws Exception {
        ArrayList<PublicationDAO> publicationDAOS = getAllIfContaints("personneId", entrepriseId, mongoClient);
        ArrayList<Publication> publications = new ArrayList<>();
        for(PublicationDAO publicationDAO : publicationDAOS)
            publications.add(publicationDAO.getPublication());
        return publications;
    }

    public PublicationDAO(Publication publication) {
        setPublication(publication);
    }

    public PublicationDAO() {
    }

    /**
     * Function @return a Json format.
     *
     * @return String
     */
    @Override
    public String toJson() {
        return gson.toJson(getPublication());
    }

    /**
     * Function to specify a name of Collection mapping class.
     *
     * @return String
     */
    @Override
    public String nameTable() {
        return Publication.class.getSimpleName();
    }

    /**
     * Function to specify a Class of object mapping data table.
     *
     * @return Class
     */
    @Override
    public Class classMap() {
        return Publication.class;
    }

    @Override
    protected String referenceId() {
        return "PUBL";
    }

    @Override
    public void save(MongoClient client) throws Exception {
        publication.setPublicationId(id());
        super.save(client);
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    private Security getConfidence(String personneId, String confidence) throws Exception {
        Security security = new Security(personneId);
        Security.class.getMethod(confidence).invoke(security);
        return security;
    }

    public void publier(MongoClient client) throws Exception {
        save(client);
    }

    public PublicationDAO publication(String publicationId, MongoClient client) throws Exception {
        return (PublicationDAO) getOneIfContaints("publicationId", publicationId, client);
    }

    public Publication getPublication(String publicationId, MongoClient client) throws Exception {
        return publication(publicationId, client).getPublication();
    }

    public ArrayList<CommentaireDAO> commentaires(MongoClient client) throws Exception {
        return new CommentaireDAO().commentaires(getPublication(), client);
    }

    public ArrayList<PublicationDAO> mesPublications(PersonneDAO personne, MongoClient client) throws Exception {
        return getAllIfContaints("personneId", personne.getUtilisateurId(), client);
    }

    public Reaction getReaction(String personneId, String publicationId, MongoClient client) throws Exception {
        ReactionDAO reaction =  new ReactionDAO().getReactionDAO(personneId, publicationId, client);
        if(reaction == null) throw new Exception("Aucune action trouver");
        return reaction.getReaction();
    }

    public String oldAction(String personneId, String publicationId, MongoClient client) throws Exception {
        try {
            Reaction reaction = getReaction(personneId, publicationId, client);
            if (reaction.isAdorer()) return "Adorer";
            if (reaction.isAimer()) return "Aimer";
            if (reaction.isDetester()) return "Detester";
        } catch (Exception e) {
            return "NoAction";
        }
        return "NoAction";
    }

    @Override
    public Publication byId(String publicationId) throws Exception {
        MongoClient mongoClient = null;
        try {
            mongoClient = client();
            PublicationDAO publicationDAO = (PublicationDAO)getOneIfContaints("publicationId", publicationId, mongoClient);
            if(publicationDAO != null)
                return publicationDAO.getPublication();
            return null;
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        finally {
            if(mongoClient != null)
                mongoClient.close();
        }
    }

    @Override
    public ArrayList<Publication> mesPublications(String personneId) throws Exception {
        MongoClient client = null;
        try {
            client = client();
            PersonneDAO personne = new PersonneDAO().getPersonneDAO(personneId, client);
            if (personne == null) throw new Exception("Impossible de trouver la personne proprietaire");

            ArrayList<Publication> publications = listDocumentObject(client);
            ArrayList<Publication> newList      = new ArrayList<>();

            for (Publication publication : publications)
                if (publication.getPersonneId().equals(personneId)){
                    publication.setReaction(oldAction(personneId, publication.getPublicationId(), client));
                    newList.add(publication);
                }
            return newList;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Override
    public void publication(String personneId, String confidence, String styleId) throws Exception {
        MongoClient client = null;
        try {
            client = client();
            new PublicationDAO(new Publication(TimeSpan.now(),
                    getConfidence(personneId, confidence), new StylesDAO().getStyles(styleId, client))).save(client);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Override
    public ArrayList<Commentaire> commentaires(String publicationId) throws Exception {
        MongoClient client = null;
        try {
            return new CommentaireDAO().commentaires(publicationId, client = client());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if(client != null) client.close();
        }
    }

    @Override
    public void doAction(String personneId, String publicaitonId, String action) throws Exception {
        MongoClient client = null;
        try {
            new ReactionDAO(new Reaction(publicaitonId, personneId), action).doReaction(client = client());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if(client != null) client.close();
        }
    }

    @Override
    public void doCommentaire(String personneId, String publicationId, String commentaire) throws Exception {
        MongoClient client = null;
        try {
            commentaire = commentaire.replace("%20", " ");
            new CommentaireDAO().add(personneId, publicationId, commentaire, client = client());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if(client != null) client.close();
        }
    }

    @Override
    public void doReponseCommentaire(String commentaireId, String personneId, String publicationId, String commentaireReponse)
            throws Exception {
        MongoClient client = null;
        try {
            new CommentaireDAO().addReponse(commentaireId, personneId, publicationId, commentaireReponse, client = client());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if(client != null) client.close();
        }
    }
}
