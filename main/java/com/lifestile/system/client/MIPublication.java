package com.lifestile.system.client;

import com.lifestile.system.client.gestion.Publication;

import java.io.Serializable;

public class MIPublication implements Serializable {
    private boolean     doAction;
    private Publication publication;

    public MIPublication(boolean doAction, Publication publication) {
        setDoAction(doAction);
        setPublication(publication);
    }

    public boolean isDoAction() {
        return doAction;
    }

    private void setDoAction(boolean action) {
        this.doAction = action;
    }

    public Publication getPublication() {
        return publication;
    }

    private void setPublication(Publication publication) {
        this.publication = publication;
    }
}
