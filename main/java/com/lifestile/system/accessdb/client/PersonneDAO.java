package com.lifestile.system.accessdb.client;

import com.api.IClientProfile;
import com.google.gson.Gson;
import com.lifestile.system.accessdb.client.gestion.*;
import com.lifestile.system.client.*;
import com.lifestile.system.client.gestion.*;
import com.mongodb.MongoClient;

import java.util.ArrayList;
import java.util.Objects;

public class PersonneDAO extends UtilisateurDAO implements IClientProfile {

    private Personne personne;

    public PersonneDAO(Personne personne) {
        setPersonne(personne);
    }

    public PersonneDAO() { }


    /**
     * Function @return a Json format.
     *
     * @return String
     */
    @Override
    public String toJson() {
        return new Gson().toJson(getPersonne());
    }

    /**
     * Function to specify a name of Collection mapping class.
     *
     * @return String
     */
    @Override
    public String nameTable() {
        return Personne.class.getSimpleName();
    }

    /**
     * Function to specify a Class of object mapping data table.
     *
     * @return Class
     */
    @Override
    public Class classMap() {
        return Personne.class;
    }

    @Override
    protected String referenceId() {
        return "PERS";
    }

    @Override
    public ProfileSecurityDAO profileSecurity(MongoClient client) throws Exception {
        return new ProfileSecurityDAO().profileSecurity(this.personne, client);
    }

    @Override
    public void updateProfileSecurity(Security security, MongoClient client) throws Exception {
        ProfileSecurityDAO profileSecurity;
        security.setReferenceId(personne.getPersonneId());
        if ((profileSecurity = profileSecurity(client)) != null) {
            profileSecurity.getProfileSecurity().setSecurity(security);
            profileSecurity.updateIfFound("personneId", personne.getPersonneId(), client);
        } else {
            new ProfileSecurityDAO(personne, security).save(client);
        }
    }

    @Override
    public String getNomComplet() {
        return getPersonne().getNom() + " " + getPersonne().getPrenom();
    }

    @Override
    public String getUtilisateurId() {
        return personne.getPersonneId();
    }


    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this.getPersonne().getPersonneId(), ((PersonneDAO) obj).getPersonne().getPersonneId());
    }

    @Override
    public void save(MongoClient client) throws Exception {
        personne.setPersonneId(id());
        super.save(client);
    }

    public PersonneDAO getPersonneDAO(String id, MongoClient client) throws Exception {
         PersonneDAO personne = (PersonneDAO) getOneIfContaints("personneId", id, client);
         if(personne == null) throw new Exception("Impossible de trouver la reference proprietaire");
         return personne;
    }


    PersonneInfoLogin login(String addresse, String password, MongoClient client) throws Exception{
        ArrayList<PersonneDAO> personnes = list(client);
        PersonneInfoLogin infoLogin = new PersonneInfoLogin(
                "", "", false, false, null, 0, 0);
        for(PersonneDAO personne : personnes){
            if(personne.getPersonne().getInfoAddress().getMails().equals(addresse)
                    || personne.getPersonne().getInfoAddress().getMobiles().equals(addresse)){
                infoLogin = new PersonneInfoLogin(
                        personne.getUtilisateurId(), personne.getNomComplet(), true, false, null, 0, 0);

                if (personne.getPersonne().getPassword().equals(password) && infoLogin.isUserValid()) {
                    infoLogin.setNombreAbonnes(new AbonnementDAO().mesAbonnes(personne.getPersonne(), client).size());
                    infoLogin.setNombreAmis(new PersonneDAO(personne.getPersonne()).listAmis(client).size());
                    infoLogin.setPassValid(true);
                    infoLogin.setPersonne(personne.getPersonne());
                }else{
                    infoLogin.setInformation("Votre mots de passe semble incorrect. Essayer de nouveau.");
                }
                return infoLogin;
            }
        }
        infoLogin.setInformation("Votre compte n'existe pas. Vous pouvez créer  gratuitement en quelque seconde");
            return infoLogin;
    }

    boolean checkAddresse(String addresse, MongoClient client) throws Exception {
        ArrayList<PersonneDAO> personnes = list(client);
        for(PersonneDAO personne : personnes){
            if(personne.getPersonne().getInfoAddress().getMails().equals(addresse)
                    || personne.getPersonne().getInfoAddress().getMobiles().equals(addresse)){
                return true;
            }
        }
        return false;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public void setAbonne(Personne personne, boolean abonne, MongoClient client) throws Exception {
        new AbonnementDAO(new Abonnement(abonne, this.getUtilisateurId(), personne.getPersonneId())).doAbonnement(client);
    }

    ArrayList<PersonneDAO> listAmis(MongoClient client) throws Exception {
        ArrayList<AmisDAO>     listAmis     = new AmisDAO().getAllAmis(this.personne, client);
        ArrayList<PersonneDAO> listPersonne = new ArrayList<>();
        for (AmisDAO amis : listAmis) {
            if (amis.getAmis().getPersonne1().equals(this.personne))
                listPersonne.add(new PersonneDAO(amis.getAmis().getPersonne2()));
            else
                listPersonne.add(new PersonneDAO(amis.getAmis().getPersonne1()));
        }
        return listPersonne;
    }

    private ArrayList<MIStylesDAO> miStyles(MongoClient client) throws Exception {
        return new MIStylesDAO().miStyle(this.personne, client);
    }

    ArrayList<PublicationDAO> mesPublications(MongoClient client) throws Exception {
        return new PublicationDAO().mesPublications(this, client);
    }

    private boolean voirStyle(MIStyles style, boolean ami, boolean amiMonAmi) {
        Security security = style.getSecurity();
        if (security.isPrive())
            return false;
        if (security.isEntreAmis())
            return ami;
        return !security.isAmisEtSesAmis() || ami || amiMonAmi;
    }

    private boolean doAction(Publication publication, boolean ami, boolean amiMonAmi) {
        Security security = publication.getSecurity();

        if (security.isAmisEtSesAmis()) return ami || amiMonAmi;

        if (security.isEntreAmis()) return ami;

        return security.isPublics();
    }

    private boolean appartient(PersonneDAO personne, ArrayList<PersonneDAO> personnes) {
        for (PersonneDAO present : personnes) {
            if (personne.equals(present)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAmiMonAmi(Personne ami, MongoClient client) throws Exception {
        ArrayList<PersonneDAO> temp1 = listAmisEtSesAmis(client);
        ArrayList<PersonneDAO> temp2 = listAmis(client);
        if (temp1.removeAll(temp2)) {
            for (PersonneDAO personne : temp1) {
                if (ami.getPersonneId().equals(personne.getPersonne().getPersonneId())) {
                    return true;
                }
            }
        } else {
            throw new Exception("Impossible d'effectué l'operation removeAll");
        }
        return false;
    }

    private ArrayList<PersonneDAO> listAmisEtSesAmis(MongoClient client) throws Exception {
        ArrayList<PersonneDAO> personnes = listAmis(client);
        ArrayList<PersonneDAO> entreAmis = new ArrayList<>();

        for (PersonneDAO personne : personnes) {
            for (PersonneDAO amis : personnes) {
                if (!appartient(amis, entreAmis)) entreAmis.add(amis);
            }
            ArrayList<PersonneDAO> sesAmis = personne.listAmis(client);
            for (PersonneDAO nouveauAmi : sesAmis) {
                if (!appartient(nouveauAmi, entreAmis)) {
                    entreAmis.add(nouveauAmi);
                }
            }
        }
        return entreAmis;
    }

    ArrayList<MIPublication> voirMIPublications(Personne personne, MongoClient client) throws Exception {
        boolean ami       = isAmi(personne, client);
        boolean amiMonAmi = isAmiMonAmi(personne, client);

        ArrayList<PublicationDAO> publications       = mesPublications(client);
        ArrayList<MIPublication>  voirMIPublications = new ArrayList<>();
        for (PublicationDAO publication : publications) {
            if (!publication.getPublication().getSecurity().isPrive()){ publication.getPublication().setReaction(
                    publication.oldAction(personne.getPersonneId(), publication.getPublication().getPublicationId(), client));
                voirMIPublications.add(new MIPublication(doAction(publication.getPublication(), ami, amiMonAmi), publication.getPublication()));
            }
        }
        return voirMIPublications;
    }

    ArrayList<MIStyles> voirMIStyles(Personne personne, MongoClient client) throws Exception {
        boolean ami       = isAmi(personne, client);
        boolean amiMonAmi = isAmiMonAmi(personne, client);

        ArrayList<MIStylesDAO> styles   = miStyles(client);
        ArrayList<MIStyles> vuStyles = new ArrayList<>();
        for (MIStylesDAO style : styles) {
            if (voirStyle(style.getMiStyles(), ami, amiMonAmi)) {
                vuStyles.add(style.getMiStyles());
            }
        }
        return vuStyles;
    }

    int nombreAmisEnCommun(Personne autrePersonne, MongoClient client) throws Exception {
        ArrayList<PersonneDAO> amis   = listAmis(client);
        ArrayList<PersonneDAO> autres = new PersonneDAO(autrePersonne).listAmis(client);
        int                    count  = 0;
        for (PersonneDAO personne : amis) {
            if (!appartient(personne, autres)) count++;
        }
        return count;
    }

    public boolean isAmi(Personne ami, MongoClient client) throws Exception {
        ArrayList<PersonneDAO> amis = listAmis(client);
        for (PersonneDAO personne : amis) {
            if (personne.getUtilisateurId().equals(ami.getPersonneId())) {
                return true;
            }
        }
        return false;
    }

    public boolean isAbonne(Personne personne, MongoClient client) throws Exception {
        ArrayList<AbonnementDAO> abonnements = new AbonnementDAO().mesAbonnes(this.getPersonne(), client);
        for (AbonnementDAO abonnement : abonnements) {
            if (abonnement.getAbonnement().getUtilisateurId().equals(personne.getPersonneId())) {
                return abonnement.getAbonnement().isAbonne();
            }
        }
        return false;
    }

    public Personne getPersonne(String personneId, MongoClient client) throws Exception{
        return getPersonneDAO(personneId, client).getPersonne();
    }

    ArrayList<Personne> amis(String personneId, MongoClient client) throws Exception {
        ArrayList<PersonneDAO> listAmisDAO = new PersonneDAO(getPersonne(personneId, client)).listAmis(client);
        ArrayList<Personne> listAmis = new ArrayList<>();
        for (PersonneDAO personne: listAmisDAO) {
            listAmis.add(personne.getPersonne());
        }
        return listAmis;
    }

    @Override
    public void addAmis(String personneId1, String personneId2) throws Exception {
        MongoClient client = null;
        try{
            client = client();
            PersonneDAO personne = new PersonneDAO();
            new AmisDAO(new Amis(this.personne = personne.getPersonne(personneId1, client),
                    personne.getPersonne(personneId2, client))).save(client);
        }finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Override
    public void addInMystyles(String personneId, String publicationId, String confidence) throws Exception {
        MongoClient client = null;
        try {
            client = client();
            Personne personne = this.getPersonne(personneId, client);
            Publication publication = new PublicationDAO().getPublication(publicationId, client);
            System.out.println(gson.toJson(publication.getBazar().getProduit()));
            new MIStylesDAO(new MIStyles(publication, personne, new Security())).addStyle(client);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }finally{
            if(client != null) client.close();
        }
    }

    @Override
    public void updateSecurityMIStyle(String personneId, String mistyleId, String confidence) throws Exception {
        MongoClient client = null;
        try{
            client = client();
            new MIStylesDAO().getMiStyles(mistyleId, new PersonneDAO().getPersonne(personneId, client), client)
                    .updateSecurity(new SecurityDAO().setSecurity(confidence), client);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }finally {
            if(client != null) client.close();
        }
    }

    @Override
    public void deleteSuggestion(String personneId, String suggestionId) throws Exception {
        MongoClient client = null;
        try{
            client = client();
            SuggestionDAO suggestionDAO = new SuggestionDAO();
            Suggestion suggestion = suggestionDAO.getSuggestion(suggestionId, client).getSuggestion();
            for (Personne personne : suggestion.getPersonnes()) {
                if (personne.getPersonneId().equals(personneId)) {
                    if(suggestion.getPersonnes().remove(personne)){
                        suggestionDAO.updateIfFound("suggestionId", suggestionId, client);
                        break;
                    }
                }
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }finally {
            if(client != null) client.close();
        }
    }

    @Override
    public ArrayList<MIStyles> voirMIStyles(String personneId1, String personneId2) throws Exception {
        MongoClient client = null;
        try {
            client = client();
            return getPersonneDAO(personneId1, client).voirMIStyles(getPersonne(personneId2, client), client);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if(client != null) client.close();
        }
    }

    @Override
    public ArrayList<MIPublication> voirMIPublications(String personneId1, String personneId2) throws Exception {
        MongoClient client = null;
        try {
            client = client();
            return getPersonneDAO(personneId1, client).voirMIPublications(getPersonne(personneId2, client), client);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Override
    public ArrayList<Personne> amis(String personneId) throws Exception {
        MongoClient client = null;
        try {
            return amis(personneId, client = client());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Override
    public ArrayList<Personne> amisASuggerer(String personneId, String publicationId) throws Exception {
        MongoClient client = null;
        try {
            client = client();
            ArrayList<Personne> listAmis   = amis(personneId, client);
            ArrayList<SuggestionDAO> suggestions = new SuggestionDAO().listSuggestions(new PersonneDAO().getPersonne(personneId, client), client);
            for (SuggestionDAO suggestion : suggestions) {
                if (suggestion.getSuggestion().getPublication().getPublicationId().equals(publicationId)) {
                    if (listAmis.removeAll(suggestion.getSuggestion().getPersonnes())) {
                        return listAmis;
                    }
                }
            }
            return listAmis;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Override
    public ArrayList<Personne> personnesSuggerer(String personneId) throws Exception {
        MongoClient client = null;
        ArrayList<Personne> suggestion = new ArrayList<>();
        try{
            client = client();
            ArrayList<PersonneDAO> amisEtSesAmis = listAmisEtSesAmis(client);
            this.setPersonne(getPersonne(personneId, client));
            if(amisEtSesAmis.removeAll(listAmis(client))){
                for (PersonneDAO personne : amisEtSesAmis) {
                    suggestion.add(personne.getPersonne());
                }
            }else{
                ArrayList<Personne> personnes = listDocumentObject(client);
                int inc = 0;
                for (Personne personne : personnes){
                    if(inc < 20){
                        if(!personne.getPersonneId().equals(personneId) && !isAmi(personne, client))
                            suggestion.add(personne);
                    }else break;
                    inc++;
                }
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }finally {
            if(client != null) client.close();
        }
        return suggestion;
    }

    @Override
    public ArrayList<MIStyles> listMIStyles(String personneId) throws Exception {
        MongoClient client = null;
        try{
            client  = client();
            ArrayList<MIStyles>  miStyles = new ArrayList<>();
            ArrayList<MIStylesDAO> styles = new MIStylesDAO().miStyle(this.getPersonne(personneId, client), client);
            for (MIStylesDAO style : styles) miStyles.add(style.getMiStyles());
            return miStyles;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }finally {
            if(client != null) client.close();
        }
    }

    @Override
    public int nombreAmisEnCommun(String personneId1, String personneId2) throws Exception {
        MongoClient client = null;
        try {
            client = client();
            return getPersonneDAO(personneId1, client).nombreAmisEnCommun(getPersonne(personneId2, client), client);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Override
    public boolean isAmi(String personneId1, String personneId2) throws Exception {
        MongoClient client = null;
        try {
            client = client();
            return getPersonneDAO(personneId1, client).isAmi(getPersonne(personneId2, client), client);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Override
    public boolean isAbonne(String personneId1, String personneId2) throws Exception {
        MongoClient client = null;
        try {
            client = client();
            return getPersonneDAO(personneId1, client).isAbonne(getPersonne(personneId2, client), client);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Override
    public boolean isExistAccount(String addresse) throws Exception {
        MongoClient client = null;
        try {
            client = client();
            return checkAddresse(addresse, client);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }finally {
            if(client != null) client.close();
        }
    }

    @Override
    public Personne inscription(String nom, String prenom, String naissance, String sexe, String mail, String pass) throws Exception {
        MongoClient client = null;
        try {

            client = client();
            InfoAddressDAO infoAddress = new InfoAddressDAO(
                    new InfoAddress("", "", "", "",mail, ""));
            infoAddress.save(client);
            nom = nom.replace("%20", " ");
            prenom = prenom.replace("%20", " ");
            PersonneDAO personne = new PersonneDAO(
                    new Personne("", nom, prenom, naissance, sexe, pass, infoAddress.getInfoAddress()));
            personne.save(client);
            return personne.getPersonne();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Override
    public PersonneInfoLogin login(String addresse, String password) throws Exception {
        MongoClient client = null;
        try{
            client = client();
            return login(addresse, password, client);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }finally {
            if(client != null) client.close();
        }
    }
}
