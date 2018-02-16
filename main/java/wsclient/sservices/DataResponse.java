package wsclient.sservices;

import com.google.gson.Gson;

class DataResponse {

    private boolean erreur;
    private Object  object;

    DataResponse(boolean erreur, Object object) {
        this.erreur = erreur;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
