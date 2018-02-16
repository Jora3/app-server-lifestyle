package com.lifestile.system.catalogue;

import java.io.Serializable;

public class Type implements Serializable {
    private String typeId;
    private String nomType;
    private String descriptionType;

    public Type(String typeId, String nomType, String descriptionType) {
        this.typeId = typeId;
        this.nomType = nomType;
        this.descriptionType = descriptionType;
    }

    public Type() {
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getNomType() {
        return nomType;
    }

    public void setNomType(String nomType) {
        this.nomType = nomType;
    }

    public String getDescriptionType() {
        return descriptionType;
    }

    public void setDescriptionType(String descriptionType) {
        this.descriptionType = descriptionType;
    }
}
