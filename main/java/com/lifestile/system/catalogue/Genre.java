package com.lifestile.system.catalogue;

import java.io.Serializable;

public class Genre implements Serializable {
    private String genreId;
    private String nomGenre;

    public Genre(String genreId, String nomGenre) {
        this.genreId = genreId;
        this.nomGenre = nomGenre;
    }
}
