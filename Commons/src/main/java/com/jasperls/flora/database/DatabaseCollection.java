package com.jasperls.flora.database;

import com.jasperls.flora.docs.Model;

import java.util.Optional;

public interface DatabaseCollection<T extends Model> {

    Optional<T> get(String id);

    void update(T document);

    void delete(String id);
}
