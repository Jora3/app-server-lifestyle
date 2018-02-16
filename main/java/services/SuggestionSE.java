package services;

import com.api.ISuggestion;
import com.lifestile.system.accessdb.client.gestion.SuggestionDAO;
import services.mapping.JsonResultats;

public class SuggestionSE extends JsonResultats {
    private final ISuggestion suggestion = new SuggestionDAO();

    public String suggestionsSysteme(String personne) {
        return jsonFormat(false, "suggestionsSysteme", personne);
    }

    @Override
    protected Object object() {
        return suggestion;
    }
}
