package services;

import com.api.IInscription;
import com.lifestile.system.accessdb.client.gestion.InscriptionDAO;
import services.mapping.JsonResultats;

public class InscriptionSE extends JsonResultats {
    private final IInscription inscription = new InscriptionDAO();

    public String inscription(String nom, String prenom, String dateNaissance, String sexe, String societe, String pays, String ville, String lieu, String mail, String mobile, String domaineId, String pass1, String pass2) {
        return jsonFormat(false, "sInscrire", nom, prenom, dateNaissance, sexe, societe, pays, ville, lieu, mail, mobile, domaineId, pass1, pass2);
    }

    @Override
    protected Object object() {
        return inscription;
    }
}
