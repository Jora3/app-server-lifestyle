package com.lifestile.system.accessdb.client.gestion;

import com.api.IActualite;
import com.lifestile.gdbo.connection.MongoDBConnection;
import com.lifestile.system.accessdb.client.PersonneDAO;
import com.lifestile.system.client.MISuggestion;
import com.lifestile.system.client.Personne;
import com.lifestile.system.client.gestion.Partager;
import com.lifestile.system.client.gestion.Publication;
import com.lifestile.system.client.gestion.Security;
import com.lifestile.system.client.gestion.Suggestion;
import com.mongodb.MongoClient;

import java.util.ArrayList;

public class ActualiteDAO implements IActualite {
    private PersonneDAO personne;

    public ActualiteDAO(PersonneDAO personne) {
        setPersonne(personne);
    }

    public ActualiteDAO() { }

    private PersonneDAO getPersonne() {
        return personne;
    }

    public void setPersonne(PersonneDAO personne) {
        this.personne = personne;
    }

    private SecurityDAO getFiltre(MongoClient client) throws Exception {
        return new SecurityDAO().security(personne.getUtilisateurId(), client);
    }

    private SecurityDAO saveIfAbsent(MongoClient client) throws Exception {
        SecurityDAO secure = getFiltre(client);
        if (secure == null) {
            new SecurityDAO(new Security(personne.getUtilisateurId())).save(client);
            secure = getFiltre(client);
        }
        return secure;
    }

    private void upGrade(SecurityDAO security, MongoClient client) throws Exception {
        SecurityDAO secure = getFiltre(client);
        if (secure != null) {
            secure.delete();
        }
        security.save(client);
    }

    private boolean isPartageEntreAmisSesAmis(Personne personne, Security publication, MongoClient client) throws Exception {
        return publication.isAmisEtSesAmis() &&
                (getPersonne().isAmi(personne, client) || getPersonne().isAmiMonAmi(personne, client));
    }

    private boolean isAccorder(PartagerDAO pub, MongoClient client) throws Exception {
        Personne personne = pub.getPartager().getPersonne();
        Security publication = pub.getPartager().getSecurity();
        Security myFiltre    = saveIfAbsent(client).getSecurity();

        if (getPersonne().getUtilisateurId().equals(personne.getPersonneId())) return true;

        if (getPersonne().isAbonne(personne, client) && !publication.isPrive()) return true;

        if (publication.isPublics()) {
            if (myFiltre.isPublics()) return true;

            if (isPartageEntreAmisSesAmis(personne, myFiltre, client)) return true;

            if (myFiltre.isEntreAmis()) return getPersonne().isAmi(personne, client);
        }
        return isPartageEntreAmisSesAmis(personne, publication, client) || publication.isEntreAmis() && getPersonne().isAmi(personne, client);
    }

    //TODO :requettes limiter. use subLit(0, 10) puis subList(11, 20) ...
    private ArrayList<Partager> mesActualites(MongoClient client) throws Exception {
        ArrayList<PartagerDAO> partages = new PartagerDAO().list(client);
        ArrayList<Partager>    actualites   = new ArrayList<>();
        PublicationDAO aPublication = new PublicationDAO();
        for (PartagerDAO partage : partages) {
            if (isAccorder(partage, client)) {
                String publicationId = partage.getPartager().getPublication().getPublicationId();
                partage.getPartager().setPublication(aPublication.getPublication(publicationId, client));
                partage.getPartager().getPublication().setReaction(aPublication.oldAction(personne.getUtilisateurId(), publicationId, client));
                actualites.add(partage.getPartager());
            }
        }
        return actualites;
    }

    private ArrayList<Publication> nouvelles(MongoClient client) throws Exception {
        ArrayList<PublicationDAO> publications = new PublicationDAO().list(client);
        ArrayList<Publication> bazars = new ArrayList<>();
        for(PublicationDAO publication : publications){
            try {
                if (publication.getPublication().getBazar().getBazarId() != null) {
                    bazars.add(publication.getPublication());
                }
            } catch (NullPointerException e) {
                System.out.println("Message erreur Ignorer : " + e.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return bazars;
    }

    private ArrayList<MISuggestion> mesSuggestions(MongoClient client) throws Exception {
        return new SuggestionDAO().suggestions(this.personne, client);
    }


    private void partager(Security security, Personne personne, Publication publication, MongoClient client) throws Exception {
        new PartagerDAO().partager(security, personne, publication, client);
    }

    @Override
    public void partager(String typeSecurity, String personneId, String publicationId) throws Exception {
        MongoClient client = null;
        try {
            client = new MongoDBConnection().getMongoClient();
            this.partager(new SecurityDAO().setSecurity(typeSecurity),
                    new PersonneDAO().getPersonne(personneId, client), new PublicationDAO().getPublication(publicationId, client), client);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Override
    public ArrayList<Publication> nouvelles(String personneId) throws Exception {
        MongoClient client = null;
        try {
            PersonneDAO personneDAO = new PersonneDAO();
            client = personneDAO.client();
            this.personne = personneDAO.getPersonneDAO(personneId, client);
            return nouvelles(client);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Override
    public ArrayList<Suggestion> listSuggerer(String publicatoinId) throws Exception {
        MongoClient client = null;
        try {
            client = new MongoDBConnection().getMongoClient();
            return new SuggestionDAO().listSuggestions(new PublicationDAO().getPublication(publicatoinId, client), client);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if(client != null) client.close();
        }
    }

    @Override
    public void filtrer(String personneId, String method) throws Exception {
        MongoClient client = null;
        try {
            client = new MongoDBConnection().getMongoClient();
            if (!method.equals("prive")) {
                this.personne = new PersonneDAO().getPersonneDAO(personneId, client);
                Security security = new Security(this.personne.getUtilisateurId());
                Security.class.getMethod(method).invoke(security);
                upGrade(new SecurityDAO(security), client);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Override
    public ArrayList<Partager> mesActualites(String personneId) throws Exception{
        MongoClient client = null;
        try {
            client = new MongoDBConnection().getMongoClient();
            return new ActualiteDAO(new PersonneDAO().getPersonneDAO(personneId, client)).mesActualites(client);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Override
    public ArrayList<MISuggestion> mesSuggestions(String personneId) throws Exception{
        MongoClient client = null;
        try {
            client = new MongoDBConnection().getMongoClient();
            this.personne = new PersonneDAO().getPersonneDAO(personneId, client);
            return mesSuggestions(client);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if(client != null){
                client.close();
            }
        }
    }

    @Override
    public void suggerer(String personneId1, String publicationId, String personneId2) throws Exception{
        MongoClient client = null;
        try {
            client = new MongoDBConnection().getMongoClient();
            PersonneDAO personneDAO = new PersonneDAO();
            Personne personne1 = personneDAO.getPersonne(personneId1, client);
            Personne personne2 = personneDAO.getPersonne(personneId2, client);
            ArrayList<SuggestionDAO> suggestions = new SuggestionDAO().listSuggestions(personne1, client);

            if(1 <= suggestions.size()){
                for (SuggestionDAO suggestion : suggestions) {
                    if (suggestion.getSuggestion().getPublication().getPublicationId().equals(publicationId)) {
                        Publication publication = new PublicationDAO().getPublication(publicationId, client);
                        publication.setNbSuggestion(publication.getNbSuggestion() + 1);
                        for (Personne p : suggestion.getSuggestion().getPersonnes()) {
                            if (p.equals(personne2)) {
                                throw new Exception("Vous avez déjà suggeré à cette personne");
                            }
                        }
                        suggestion.getSuggestion().getPersonnes().add(personne2);
                        suggestion.updateIfFound("suggestionId", suggestion.getSuggestion().getSuggestionId(), client);
                        new PublicationDAO(publication).updateIfFound("publicationId", publicationId, client);
                        return;
                    }
                }
            }
            ArrayList<Personne> personnes = new ArrayList<>();
            personnes.add(personne2);
            new SuggestionDAO(new Suggestion(personne1, new PublicationDAO().getPublication(publicationId, client),  personnes)).save(client);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Override
    public void annulerSuggestion(String personneId1, String publicationId, String personneId2) throws Exception {
        MongoClient client = null;
        try {
            client = new MongoDBConnection().getMongoClient();
            PersonneDAO personneDAO = new PersonneDAO();
            Personne personne1 = personneDAO.getPersonne(personneId1, client);
            Personne personne2 = personneDAO.getPersonne(personneId2, client);
            ArrayList<SuggestionDAO> suggestions = new SuggestionDAO().listSuggestions(personne1, client);

            if(suggestions.size() == 0){
                throw new Exception("Vous n'avez pas encore effectué une suggestion");
            }else{
                for (SuggestionDAO suggestion : suggestions) {
                    if (suggestion.getSuggestion().getPublication().getPublicationId().equals(publicationId)) {
                        for (Personne p : suggestion.getSuggestion().getPersonnes()) {
                            if (p.getPersonneId().equals(personne2.getPersonneId())) {
                                suggestion.getSuggestion().getPersonnes().remove(personne2);
                                suggestion.updateIfFound("suggestionId", suggestion.getSuggestion().getSuggestionId(), client);
                                return;
                            }
                        }
                    }
                }
            }
            throw new Exception("La personne n'appartient dans aucune suggestion");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if (client != null) {
                client.close();
            }
        }
    }

}
