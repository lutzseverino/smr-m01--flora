package com.jasperls.flora.database;

import com.jasperls.flora.docs.Model;

public interface DatabaseManager {
    <T extends Model> DatabaseCollection<T> getCollection(String name, Class<T> type);
}
