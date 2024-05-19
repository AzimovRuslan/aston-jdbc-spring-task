package io.aston.serverside.dao;

import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public interface DAOInterface<T> {
    List<T> getAll() throws SQLException;

    T getById(long id) throws SQLException;

    void save(T t) throws SQLException;

    void update(long id, T t) throws SQLException;

    void deleteById(long id) throws SQLException;
}
