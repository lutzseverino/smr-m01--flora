package com.jasperls.flora.database;

import com.google.inject.ImplementedBy;
import com.jasperls.flora.database.mongodb.MongoDatabaseManager;
import com.jasperls.flora.docs.Model;

@ImplementedBy(MongoDatabaseManager.class)
public interface DatabaseManager {

    <T extends Model> DatabaseCollection<T> getCollection(Class<T> type);
}
