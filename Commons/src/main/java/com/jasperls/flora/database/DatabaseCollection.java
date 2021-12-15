package com.jasperls.flora.database;

import com.jasperls.flora.docs.Model;

public interface DatabaseCollection<T extends Model> {
    T get(String id);

    T update(T document);

    void delete(String id);
}
