package com.api;

import com.lifestile.system.catalogue.Bazar;

import java.util.ArrayList;

public interface ISuggestion {
    ArrayList<Bazar> suggestionsSysteme(String personne) throws Exception;
}
