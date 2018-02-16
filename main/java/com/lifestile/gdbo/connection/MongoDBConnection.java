package com.lifestile.gdbo.connection;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.MongoExecutionTimeoutException;
import com.mongodb.client.MongoCollection;

public class MongoDBConnection {

    public static String DBNAME = "lifestyle";

    public MongoClient getMongoClient() throws Exception {
        try {
            String URI = "mongodb://rootstyle:rootroot@ds046067.mlab.com:46067/lifestyle";
            //String URI = "mongodb://localhost:27017";
            return new MongoClient(new MongoClientURI(URI));
        } catch (MongoExecutionTimeoutException time) {
            throw new Exception("Erreur connection perdue. Cause : Time out 3000ms");
        } catch (MongoException mongo) {
            throw new MongoException(mongo.getMessage());
        }
    }

    public MongoCollection getCollections(String aTableName, MongoClient client) {
        return client.getDatabase(DBNAME).getCollection(aTableName);
    }
}
