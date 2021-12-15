package com.jasperls.flora.database;

import com.jasperls.flora.database.mongodb.MongoCollection;
import com.jasperls.flora.docs.Model;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.mongojack.JacksonMongoCollection;

public class MongoDatabaseManager implements DatabaseManager {
    private final MongoDatabase mongoDatabase;

    public MongoDatabaseManager(String host, int port, String username, String password, String name) {
        String authenticationString = String.format("mongodb://%s:%s@%s:%d/?readPreference=primary&ssl=false", username, password, host, port);

        MongoClient mongoClient = MongoClients.create(authenticationString);
        this.mongoDatabase = mongoClient.getDatabase(name);
    }

    @Override
    public <T extends Model> DatabaseCollection<T> getCollection(String name, Class<T> type) {
        return new MongoCollection<>(
                JacksonMongoCollection.builder().build(this.mongoDatabase, name, type, UuidRepresentation.STANDARD
                ));
    }
}
