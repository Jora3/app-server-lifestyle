package com.lifestile.system.accessdb.client.gestion;

import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.accessdb.client.PersonneDAO;
import com.lifestile.system.client.gestion.Commentaire;
import com.lifestile.system.client.gestion.Publication;
import com.lifestile.system.client.gestion.ReponseCommentaire;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.ArrayList;

@SuppressWarnings({"ALL"})
public class CommentaireDAO extends ClassMapTable {
    private Commentaire commentaire;

    public CommentaireDAO(Commentaire commentaire) {
        setCommentaire(commentaire);
    }

    public CommentaireDAO() {
    }

    @Override
    public String toJson() {
        return gson.toJson(getCommentaire());
    }

    @Override
    public String nameTable() {
        return Commentaire.class.getSimpleName();
    }

    @Override
    public Class classMap() {
        return Commentaire.class;
    }

    @Override
    protected String referenceId() {
        return "COMT";
    }

    @Override
    public void save(MongoClient client) throws Exception{
        commentaire.setCommentaireId(id());
        super.save(client);
    }

    public Commentaire getCommentaire() {
        return this.commentaire;
    }

    public void setCommentaire(Commentaire commentaire) {
        this.commentaire = commentaire;
    }

    ArrayList<CommentaireDAO> commentaires(Publication publication, MongoClient client) throws Exception {
        return getAllIfContaints("publicationId", publication.getPublicationId(), client);
    }

    ArrayList<Commentaire> commentaires(String publicationId, MongoClient client) throws Exception {
        ArrayList<CommentaireDAO> listCommentaires = this.commentaires(new PublicationDAO().publication(publicationId, client).getPublication(), client);
        ArrayList<Commentaire>    commentaires     = new ArrayList<>();
        for (CommentaireDAO pub : listCommentaires) {
            commentaires.add(pub.getCommentaire());
        }
        return commentaires;
    }

    private void setLastCommentaire(boolean isCommentaire, String publicationId, Commentaire commentaire, MongoClient client) throws Exception {
        Publication publication = new PublicationDAO().getPublication(publicationId, client);
        publication.setLastCommentaire(commentaire);
        if(isCommentaire) publication.setNbCommentaire(publication.getNbCommentaire() + 1);
        new PublicationDAO(publication).updateIfFound("publicationId", publicationId, client);
    }

    void add(String personneId, String publicationId, String commentaire, MongoClient client) throws Exception {
        CommentaireDAO comment = new CommentaireDAO(
                new Commentaire(
                        personneId,
                        new PersonneDAO().getPersonneDAO(personneId, client).getNomComplet(),
                        publicationId,
                        commentaire,
                        null));
        setLastCommentaire(true, publicationId, comment.getCommentaire(), client);
        comment.save(client);
    }

    void addReponse(String commentaireId, String personneId, String publicationId, String commentaireReponse, MongoClient client) throws Exception {
        MongoCursor<Document> cursor = null;
        try {
            cursor = mongoCursor(client);
            commentaireReponse = commentaireReponse.replace("%20", " ");
            while (cursor.hasNext()) {
                Document document = cursor.next();
                if (document.get("publicationId").equals(publicationId) && document.get("commentaireId").equals(commentaireId)) {
                    CommentaireDAO commentaire = new CommentaireDAO(gson.fromJson(document.toJson(), Commentaire.class)
                                    .addReponse(new ReponseCommentaire(personneId, new PersonneDAO().getPersonneDAO(personneId, client).getNomComplet(),
                                            commentaireReponse)));
                            commentaire.updateIfFounds("publicationId, commentaireId", publicationId+", "+commentaireId, client);
                    setLastCommentaire(false, publicationId, commentaire.getCommentaire(), client);
                    break;
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (cursor != null) cursor.close();
        }
    }
}
