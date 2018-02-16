package com.lifestile.system.accessdb.client.gestion;

import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.client.gestion.Publication;
import com.lifestile.system.client.gestion.Reaction;
import com.mongodb.MongoClient;

import java.lang.reflect.Method;

@SuppressWarnings({"ALL"})
public class ReactionDAO extends ClassMapTable {

    private final String keys = "publicationId, personneId";
    private String   action;
    private Reaction reaction;

    public ReactionDAO(Reaction reaction, String action) {
        setReaction(reaction);
        setAction(action);
    }

    public ReactionDAO() {
    }

    @Override
    public String toJson() {
        return gson.toJson(reaction);
    }

    @Override
    public String nameTable() {
        return Reaction.class.getSimpleName();
    }

    @Override
    public Class classMap() {
        return Reaction.class;
    }

    @Override
    protected String referenceId() {
        return "REAC";
    }

    private String getValuesId() {
        return this.reaction.getPublicationId() + ", " + this.reaction.getPersonneId();
    }

    private Method setBoolean(Object o, String action) throws NoSuchMethodException {
        return o.getClass().getMethod("set" + action, boolean.class);
    }

    private Method setInteger(Object o, String action) throws NoSuchMethodException {
        return o.getClass().getMethod("set" + action, int.class);
    }

    private boolean getBoolean(Object o, String action) throws Exception {
        return (boolean) o.getClass().getMethod("is" + action).invoke(o);
    }

    private void reAimerDetester(Publication publication, Reaction old) throws Exception {
        String action = "";
        if (getAction().equals("Aimer")) {
            if(getBoolean(old, "Detester")) action = "Detester";
            if(getBoolean(old, "Adorer")) action = "Adorer";
        }

        if (getAction().equals("Detester")) {
            if(getBoolean(old, "Aimer")) action = "Aimer";
            if(getBoolean(old, "Adorer")) action = "Adorer";
        }

        if (getAction().equals("Adorer")) {
            if(getBoolean(old, "Aimer")) action = "Aimer";
            if(getBoolean(old, "Detester")) action = "Detester";
        }

        if (!action.equals("")) {
            setBoolean(old, action).invoke(old, false);
            setInteger(publication, action).invoke(publication, -1);
        }
    }

    private void updates(PublicationDAO publication, ReactionDAO reaction, MongoClient client) throws Exception {
        publication.updateIfFound("publicationId", publication.getPublication().getPublicationId(), client);
        reaction.updateIfFounds(keys, getValuesId(), client);
    }

    private void update(PublicationDAO publication, MongoClient client) throws Exception {
        publication.updateIfFound("publicationId", publication.getPublication().getPublicationId(), client);
        this.save(client);
    }

    //TODO :  gestion de la reaction partager et suggerer.
    void doReaction(MongoClient client) throws Exception {
        Method reactionSet;
        Method publicationSet;

        ReactionDAO reactionDAO  = (ReactionDAO) getOneIfContaints(keys, getValuesId(), client);
        PublicationDAO         publication = new PublicationDAO().publication(reaction.getPublicationId(), client);
        if(publication == null)
            throw new Exception("Aucune publication trouvé. Cette publication a éte supprimer.");

        publicationSet = setInteger(publication.getPublication(), getAction());

        if (reactionDAO != null) {
            Reaction old      = reactionDAO.getReaction();
            boolean  isActive = getBoolean(old, getAction());
            reactionSet = setBoolean(old, getAction());

            if (isActive) {
                reactionSet.invoke(old, false);
                publicationSet.invoke(publication.getPublication(), -1);
            } else {
                reactionSet.invoke(old, true);
                publicationSet.invoke(publication.getPublication(), 1);
                reAimerDetester(publication.getPublication(), old);
            }
            setReaction(old);
            updates(publication, this, client);
        }else {
            setBoolean(getReaction(), getAction()).invoke(getReaction(), true);
            publicationSet.invoke(publication.getPublication(), 1);
            update(publication, client);
        }
    }

    ReactionDAO getReactionDAO(String personneId, String publicationId, MongoClient client) throws Exception {
        return (ReactionDAO) getOneIfContaints(keys, publicationId+", "+personneId, client);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }

}
