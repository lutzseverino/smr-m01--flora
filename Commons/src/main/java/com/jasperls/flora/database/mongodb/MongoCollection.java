package com.jasperls.flora.database.mongodb;

import com.jasperls.flora.database.DatabaseCollection;
import com.jasperls.flora.docs.Model;
import org.mongojack.JacksonMongoCollection;

public class MongoCollection<T extends Model> implements DatabaseCollection<T> {

    private JacksonMongoCollection<T> connection;

    public MongoCollection(JacksonMongoCollection<T> connection) {
        this.connection = connection;
    }

    @Override
    public T get(String id) {
        return this.connection.findOneById(id);
    }

    @Override
    public T update(T document) {
        this.connection.save(document);
        return document;
    }

    @Override
    public void delete(String id) {
        this.connection.removeById(id);
    }
}
