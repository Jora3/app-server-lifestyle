package com.lifestile.system.accessdb.catalogue;

import com.lifestile.gdbo.mapping.ClassMapTable;
import com.lifestile.system.catalogue.Styles;
import com.mongodb.MongoClient;

public class    StylesDAO extends ClassMapTable {

    private Styles styles;


    public StylesDAO(Styles styles) {
        setStyles(styles);
    }

    public StylesDAO() {
    }

    /**
     * Function @return a Json format.
     *
     * @return String
     */
    @Override
    public String toJson() {
        return gson.toJson(getStyles());
    }

    /**
     * Function to specify a name of Collection mapping class.
     *
     * @return String
     */
    @Override
    public String nameTable() {
        return "Styles";
    }

    /**
     * Function to specify a Class of object mapping data table.
     *
     * @return Class
     */
    @Override
    public Class classMap() {
        return Styles.class;
    }

    @Override
    protected String referenceId() {
        return "STLS";
    }

    @Override
    public void save(MongoClient client) throws Exception {
        styles.setStyleId(id());
        super.save(client);
    }

    public Styles getStyles() {
        return styles;
    }

    public void setStyles(Styles styles) {
        this.styles = styles;
    }

    public Styles getStyles(String styleId, MongoClient client) throws Exception {
        StylesDAO styles = (StylesDAO) getOneIfContaints("styleId", styleId, client);
        return styles.getStyles();
    }
}
