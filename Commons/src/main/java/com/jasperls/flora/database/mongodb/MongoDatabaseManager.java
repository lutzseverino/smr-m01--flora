package com.jasperls.flora.database.mongodb;

import com.google.common.collect.Maps;
import com.jasperls.flora.config.DatabaseConfig;
import com.jasperls.flora.database.DatabaseCollection;
import com.jasperls.flora.database.DatabaseManager;
import com.jasperls.flora.database.annotations.CollectionName;
import com.jasperls.flora.docs.Model;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.mongojack.JacksonMongoCollection;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class MongoDatabaseManager implements DatabaseManager {
    private final MongoDatabase mongoDatabase;
    private final Map<String, DatabaseCollection<?>> collections = Maps.newHashMap();

    @Inject
    public MongoDatabaseManager(DatabaseConfig databaseConfig) {
        String authenticationString = String.format(
                "mongodb://%s:%s@%s:%d/?readPreference=primary&ssl=false",
                databaseConfig.getName(),
                databaseConfig.getPasswd(),
                databaseConfig.getHost(),
                databaseConfig.getPort());

        MongoClient mongoClient = MongoClients.create(authenticationString);
        this.mongoDatabase = mongoClient.getDatabase(databaseConfig.getName());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Model> DatabaseCollection<T> getCollection(Class<T> type) {
        CollectionName collectionName = type.getAnnotation(CollectionName.class);
        String name = collectionName == null ? type.getName().toLowerCase() : collectionName.value();

        return (DatabaseCollection<T>) collections.computeIfAbsent(
                name, s -> new MongoCollection<>(JacksonMongoCollection.builder().build(this.mongoDatabase, name, type, UuidRepresentation.STANDARD)));
    }
}
