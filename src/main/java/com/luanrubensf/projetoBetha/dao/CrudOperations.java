package com.luanrubensf.projetoBetha.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Rubens
 */
public interface CrudOperations<T> {
    T persist(T entity) throws Exception ;
    List<T> findAll() throws Exception;
    T findById(Long id) throws Exception;
    void delete(Long id) throws Exception;
}