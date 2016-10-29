package com.luanrubensf.projetoBetha.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Rubens
 */
public interface CrudOperations<T> {
    T persist(T entity) throws SQLException;
    List<T> findAll() throws SQLException;
    T findById(Long id) throws SQLException;
    void delete(Long id) throws SQLException;
}