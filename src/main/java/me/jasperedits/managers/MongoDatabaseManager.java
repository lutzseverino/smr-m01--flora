package me.jasperedits.managers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import me.jasperedits.docs.db.Model;
import org.bson.UuidRepresentation;
import org.mongojack.JacksonMongoCollection;

public class MongoDatabaseManager implements DatabaseManager {

    private final MongoDatabase mongoDatabase;

    public MongoDatabaseManager(String host, int port, String username, String password, String database) {
        String authenticationString = String.format("mongodb://%s:%s@%s:%d/?readPreference=primary&ssl=false", username, password, host, port);

        MongoClient mongoClient = MongoClients.create(authenticationString);
        this.mongoDatabase = mongoClient.getDatabase(database);
    }

    @Override
    public <T extends Model> JacksonMongoCollection<T> getCollection(String name, Class<T> type) {
        return JacksonMongoCollection.builder().build(this.mongoDatabase, name, type, UuidRepresentation.STANDARD);
    }
}
