package com.lifestile.system.client.gestion;

import com.lifestile.util.TimeSpan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Commentaire implements Serializable {
    private String                        commentaireId;
    private String                        publicationId;
    private String                        personneId;
    private String                        personneNom;
    private String                        commentaire;
    private String                        commentaireDate;

    private ArrayList<ReponseCommentaire> reponseCommentaires;

    public Commentaire(String personneId, String nomComplet, String publicationId, String commentaire, ArrayList<ReponseCommentaire> reponseCommentaires) {
        setPublicationId(publicationId);
        setPersonneId(personneId);
        setPersonneNom(nomComplet);
        setCommentaire(commentaire);
        setReponseCommentaires(reponseCommentaires);
        setCommentaireDate(TimeSpan.now());
    }

    public Commentaire() { }

    public String getCommentaireId() {
        return commentaire;
    }

    public void setCommentaireId(String commentaireId) {
        this.commentaireId = commentaireId;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }

    public String getCommentaireDate() {
        return commentaireDate;
    }

    private void setCommentaireDate(String date) {
        this.commentaireDate = date;
    }

    public ArrayList<ReponseCommentaire> getReponseCommentaires() {
        return reponseCommentaires;
    }

    public void setReponseCommentaires(ArrayList<ReponseCommentaire> reponseCommentaires) {
        this.reponseCommentaires = reponseCommentaires;
    }

    public void setPersonneNom(String nomComplet){
        this.personneNom = nomComplet;
    }

    public String getPersonneNom(){
        return personneNom;
    }

    public String getPersonneId() {
        return personneId;
    }

    public void setPersonneId(String personneId) {
        this.personneId = personneId;
    }

    public Commentaire addReponse(ReponseCommentaire reponseCommentaire) {
        if (reponseCommentaires == null) reponseCommentaires = new ArrayList<>();
        this.reponseCommentaires.add(reponseCommentaire);
        return this;
    }
}
