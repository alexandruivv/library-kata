package com.softwaremind.librarykata.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CrudService<T> {
    T save(T entity);
    Optional<T> findById(UUID id);
    List<T> findAll();
    void delete(UUID id);
}
