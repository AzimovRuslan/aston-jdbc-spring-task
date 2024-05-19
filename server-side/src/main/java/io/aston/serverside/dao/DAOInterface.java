package io.aston.serverside.dao;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DAOInterface<T> {
    List<T> getAll();

    T getById();

    void save(T t);

    void update(long id, T t);

    void delete(long id);
}
