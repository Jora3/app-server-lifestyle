package com.lifestile.system.client.gestion;

import java.io.Serializable;
import java.util.Date;

public class ReponseCommentaire implements Serializable {
    private String personneId;
    private String personneNom;
    private Date   reponseDate;
    private String commentaire;

    public ReponseCommentaire(String personneId, String nomComplet, String commentaire) {
        setPersonneId(personneId);
        setPersonneNom(nomComplet);
        setCommentaire(commentaire);
        setReponseDate(new Date());
    }

    public String getPersonneId() {
        return personneId;
    }

    public void setPersonneId(String personneId) {
        this.personneId = personneId;
    }

    public void setPersonneNom(String personneNom){
        this.personneNom = personneNom;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getReponseDate() {
        return reponseDate;
    }

    public void setReponseDate(Date date) {
        this.reponseDate = date;
    }
}

