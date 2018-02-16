package com.lifestile.system.client;

import com.lifestile.system.catalogue.Bazar;
import com.lifestile.system.catalogue.Styles;
import com.lifestile.system.client.gestion.Publication;
import com.lifestile.system.client.gestion.Security;

import java.io.Serializable;

public class MIStyles implements Serializable {
    private String   mistylesId;
    private String   personneId;
    private String   publicationId;
    private Styles   styles;
    private Security security;

    public MIStyles(Publication publication, Personne personne, Security security) throws Exception {
        setPersonneId(personne);
        setStyles(publication.getBazar(), personne);
        getStyles().setPersonne(personne);
        security.setReferenceId(getPersonneId());
        setSecurity(security);
        setPublicationId(publication.getPublicationId());
    }

    public void setStyles(Bazar bazar, Personne personne) {
        setStyles(new Styles(bazar.getBazarId(), bazar.getProduit(), personne));
    }

    public MIStyles() {}

    public String getMistylesId(){
        return mistylesId;
    }

    public void setMistylesId(String mistylesId) {
        this.mistylesId = mistylesId;
    }

    public String getPersonneId() {
        return personneId;
    }

    public void setPersonneId(Personne personne) throws Exception {
        if (personne == null) throw new Exception("Impossible d'ajouter ce style");
        this.personneId = personne.getPersonneId();
    }

    public Styles getStyles() {
        return styles;
    }

    void setStyles(Styles styles) {
        this.styles = styles;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public String getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }
}
