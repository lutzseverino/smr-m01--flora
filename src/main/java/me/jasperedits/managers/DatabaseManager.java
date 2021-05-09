package me.jasperedits.managers;

import com.mongodb.client.MongoCollection;
import me.jasperedits.docs.Model;

public interface DatabaseManager {

    <T extends Model> MongoCollection<T> getCollection(String name, Class<T> type);

}
