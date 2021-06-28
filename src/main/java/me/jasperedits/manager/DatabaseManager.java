package me.jasperedits.manager;

import me.jasperedits.docs.db.Model;
import org.mongojack.JacksonMongoCollection;

public interface DatabaseManager {

    <T extends Model> JacksonMongoCollection<T> getCollection(String name, Class<T> type);

}
