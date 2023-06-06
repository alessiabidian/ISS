package com.example.magazinonlineapp.repository;
import com.example.magazinonlineapp.domain.Entity;
import com.example.magazinonlineapp.domain.validators.ValidationException;

import java.util.List;

public interface Repository<ID, E extends Entity<ID>> {
    E findByID(ID id);

    E findOne(E incompleteObject);

    Iterable<E> findAll();

    E add(E entity) throws ValidationException;

    E delete(ID id);

    void update(ID id, E entity) throws ValidationException;

    List<E> getAllAsList();

}
