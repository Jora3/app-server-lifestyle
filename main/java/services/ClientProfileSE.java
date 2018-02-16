package services;

import com.api.IClientProfile;
import com.lifestile.system.accessdb.client.PersonneDAO;
import services.mapping.JsonResultats;

public class ClientProfileSE extends JsonResultats{
    
    private final IClientProfile profile = new PersonneDAO();

    public String inscription(String nom, String prenom, String naissance, String sexe, String mail, String pass){
        return jsonFormat(false, "inscription", nom, prenom, naissance, sexe, mail, pass);
    }

    public String checkAccount(String addresse){
        return jsonFormat(false, "isExistAccount", addresse);
    }

    public String login(String addresse, String password){
        return jsonFormat(false, "login", addresse, password);
    }

    public String deleteSuggestion(String personneId, String suggestionId){
        return jsonFormat(true, "deleteSuggestion", personneId, suggestionId);
    }

    public String voirMIStyles(String personneId1, String personneId2) {
        return jsonFormat(false, "voirMIStyles", personneId1, personneId2);
    }

    public String voirMIPublications(String personneId1, String personneId2) {
        return jsonFormat(false, "voirMIPublications", personneId1, personneId2);
    }

    public String nombreAmisEnCommun(String personneId1, String personneId2) {
        return jsonFormat(false, "nombreAmisEnCommun", personneId1, personneId2);
    }

    public String addAmis(String personneId1, String personneId2) {
        return jsonFormat(true, "addAmis", personneId1, personneId2);
    }

    public String isAmi(String personneId1, String personneId2) {
        return jsonFormat(false, "isAmi", personneId1, personneId2);
    }

    public String isAbonne(String personneId1, String personneId2) {
        return jsonFormat(false, "isAbonne", personneId1, personneId2);
    }

    public String listAmis(String personneId) {
        return jsonFormat(false, "amis", personneId);
    }

    public String listAmisASuggerer(String personneId, String publicationId){
        return jsonFormat(false, "amisASuggerer", personneId, publicationId);
    }

    public String listMIStyles(String personneId){
        return jsonFormat(false, "listMIStyles", personneId);
    }

    public String addInMystyles(String personneId, String publicationId, String confidence){
        return jsonFormat(true, "addInMystyles", personneId, publicationId, confidence);
    }

    public String updateSecurityMIStyle(String personneId, String mistylesId, String confidence){
        return jsonFormat(true, "updateSecurityMIStyle", personneId, mistylesId, confidence);
    }

    public String personnesSuggerer(String personneId){
        return jsonFormat(false, "personnesSuggerer", personneId);
    }

    @Override
    protected Object object() {
        return profile;
    }
}
