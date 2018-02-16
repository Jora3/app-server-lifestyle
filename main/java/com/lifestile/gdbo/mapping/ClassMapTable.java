package com.lifestile.gdbo.mapping;

import com.google.gson.Gson;
import com.lifestile.gdbo.connection.MongoDBConnection;
import com.lifestile.util.TimeSpan;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings({"ALL"})
public abstract class ClassMapTable {
    protected final Gson gson = new Gson();

    /**
     * Function @return a Json format.
     *
     * @return String
     */
    public abstract String toJson();

    /**
     * Function to specify a name of Collection mapping class.
     *
     * @return String
     */
    public abstract String nameTable();

    /**
     * Function to specify a Class of getObject mapping data table.
     *
     * @return Class
     */
    public abstract Class classMap();

    protected abstract String referenceId();

    protected String id() throws Exception {
        if(referenceId() == null) throw new Exception("Impossible de definire la referenceId");
        else{
            if(referenceId().length() != 4) throw new Exception("La referenceId doit contenir 4 caracteres");
            return (100 + new Random().nextInt(999 - 100))+referenceId().toUpperCase()+TimeSpan.timesTemp();
        }
    }

    public MongoClient client() throws Exception {
        return new MongoDBConnection().getMongoClient();
    }

    private BsonDocument bsonDecument() {
        return BsonDocument.parse(toJson());
    }

    /**
     * Regenrer l'Id de l'Object.*/
    public void save(MongoClient client) throws Exception{
        new MongoDBConnection().getCollections(nameTable(), client).insertOne(Document.parse(toJson()));
    }

    /**
     * Mis ajours des donnees sans generation de l'Id de l'Object*/
    private void save(MongoClient client, String noAgrs) {
        new MongoDBConnection().getCollections(nameTable(), client).insertOne(Document.parse(toJson()));
    }

    protected DeleteResult delete(MongoClient client) {
        return client.getDatabase(MongoDBConnection.DBNAME).getCollection(nameTable()).deleteOne(bsonDecument());
    }

    public DeleteResult delete() throws Exception {
        MongoClient client = null;
        try {
            return delete(client);
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        } finally {
            if (client != null) client.close();
        }
    }

    private String[] convertTo(String s) {
        s = s.replace(" ", "");
        return s.split(",");
    }

    private void equalsLength(String s1[], String s2[]) throws Exception {
        if (s1.length != s2.length) throw new Exception("Keys et values doivent avoir le meme nombre");
    }

    private boolean isContainsKeys(Document document, String[] keys, String[] values, int taille) {
        for (int i = 0; i < taille; i++) {
            if (!document.get(keys[i]).equals(values[i])) {
                return false;
            }
        }
        return true;
    }

    private Object getObject(String aJson, Method methodSet) throws InstantiationException, InvocationTargetException, IllegalAccessException {
        try {
            Object object = getClass().newInstance();
            methodSet.invoke(object, gson.fromJson(aJson, classMap()));
            return object;
        } catch (InstantiationException instance) {
            throw new InstantiationException("Impossible d'obtenir l'objet. Cause :" + instance.getMessage());
        }
    }

    protected MongoCursor mongoCursor(MongoClient client) {
        return new MongoDBConnection().getCollections(nameTable(), client).find().iterator();
    }

    private MongoCursor mongoCursor(Bson bson, MongoClient client) {
        return new MongoDBConnection().getCollections(nameTable(), client).find(bson).iterator();
    }

    private Method methodSet() throws NoSuchMethodException {
        return getClass().getMethod("set" + nameTable(), classMap());
    }

    /**
     * TODO : requette limiter
     */
    public ArrayList listDocumentObject(MongoClient client) throws Exception {
        MongoCursor<Document> cursor = null;
        try {
            cursor = mongoCursor(client);
            ArrayList list = new ArrayList();
            while (cursor.hasNext()) {
                list.add(gson.fromJson(cursor.next().toJson(), classMap()));
            }
            return list;

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception(exception.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public ArrayList list(MongoClient client) throws Exception {
        MongoCursor<Document> cursor = null;
        try {
            cursor = mongoCursor(client);
            Method    methodSet = methodSet();
            ArrayList list      = new ArrayList();
            while (cursor.hasNext()) {
                Document document = cursor.next();
                list.add(getObject(document.toJson(), methodSet));
            }
            return list;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception(exception.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    /**
     * This method is necessary only to find a Document contains a simple key = value.
     *
     * @param keys   must separate by comma (,) if any key.
     * @param values must separate by comma (,) if any value
     * @return Object
     * @see Object
     */

    protected Object getOneIfContaints(String keys, String values, MongoClient client) throws Exception {
        MongoCursor<Document> cursor = null;
        String                tabKeys[];
        String                tabValues[];
        try {
            equalsLength(tabKeys = convertTo(keys), tabValues = convertTo(values));
            cursor = mongoCursor(client);
            while (cursor.hasNext()) {
                Document document = cursor.next();
                if (isContainsKeys(document, tabKeys, tabValues, tabKeys.length))
                    return getObject(document.toJson(), methodSet());
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception(exception.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    /**
     * This method is necessary only to find collections contains a simple key = value none object.
     *
     * @param keys   must separate by comma (,) if any key.
     * @param values must separate by comma (,) if any value
     * @return ArrayList
     * @see ArrayList
     */
    protected ArrayList getAllIfContaints(String keys, String values, MongoClient client) throws Exception {
        MongoCursor<Document> cursor = null;
        String                tabKeys[];
        String                tabValues[];

        try {
            equalsLength(tabKeys = convertTo(keys), tabValues = convertTo(values));
            cursor = mongoCursor(client);
            Method    methodSet = methodSet();
            ArrayList list      = new ArrayList();
            while (cursor.hasNext()) {
                Document document = cursor.next();
                if (isContainsKeys(document, tabKeys, tabValues, tabKeys.length))
                    list.add(getObject(document.toJson(), methodSet));
            }
            return list;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception(exception.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
    }


    /**
     * Only use to return if an object exist in databases.
     *
     * @param key   String keys
     * @param value Object format Json.
     * @return Object can be casted.
     * @throws Exception if error catch
     */
    protected Object getOneIfExist(String key, String value, MongoClient client) throws Exception {
        MongoCursor<Document> cursor = null;
        try {
            cursor = mongoCursor(client);
            while (cursor.hasNext()) {
                Document document = cursor.next();
                if (gson.toJson(document.get(key)).equals(value))
                    return getObject(document.toJson(), methodSet());
            }
            return null;
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    /**
     * @param key   String keys.
     * @param value Object format Json.
     * @return ArrayList
     * @throws Exception we can found.
     */
    protected ArrayList getAllIfExist(String key, String value, MongoClient client) throws Exception {
        MongoCursor<Document> cursor = null;
        try {
            cursor = mongoCursor(client);
            Method    methodSet = methodSet();
            ArrayList list      = new ArrayList();
            while (cursor.hasNext()) {
                Document document = cursor.next();
                if (gson.toJson(document.get(key)).equals(value)) list.add(getObject(document.toJson(), methodSet));
            }
            return list;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception(exception.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public void updateIfFound(String key, String value, MongoClient client) throws Exception {
        MongoCursor<Document> cursor = null;
        try {
            cursor = mongoCursor(client);
            while (cursor.hasNext()) {
                Document document = cursor.next();
                if (document.get(key).equals(value)) {
                    new MongoDBConnection().getCollections(nameTable(), client).deleteOne(BsonDocument.parse(document.toJson()));
                    save(client, null);
                    break;
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    protected void updateIfFounds(String keys, String values, MongoClient client) throws Exception {
        MongoCursor<Document> cursor = null;
        String                tabKeys[];
        String                tabValues[];
        try {
            equalsLength(tabKeys = convertTo(keys), tabValues = convertTo(values));
            cursor = mongoCursor(client);
            while (cursor.hasNext()) {
                Document document = cursor.next();
                if (isContainsKeys(document, tabKeys, tabValues, tabKeys.length)) {
                    new MongoDBConnection().getCollections(nameTable(), client).deleteOne(BsonDocument.parse(document.toJson()));
                    save(client, null);
                    break;
                }
            }
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
    }
}
