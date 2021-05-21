package me.jasperedits.managers;

import com.mongodb.client.MongoCollection;
import me.jasperedits.docs.base.Model;
import org.mongojack.JacksonMongoCollection;

public interface DatabaseManager {

    <T extends Model> JacksonMongoCollection<T> getCollection(String name, Class<T> type);

}
