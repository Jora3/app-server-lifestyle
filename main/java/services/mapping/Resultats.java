package services.mapping;

import com.google.gson.Gson;

@SuppressWarnings({"All"})
public class Resultats {
    private boolean erreur;
    private Object  object;

   public Resultats(Object o, boolean isErreur) {
        this.object = o;
        this.erreur = isErreur;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String toString() {
        return toJson();
    }
}
