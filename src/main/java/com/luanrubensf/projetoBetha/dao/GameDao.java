
package com.luanrubensf.projetoBetha.dao;

import com.luanrubensf.projetoBetha.model.Game;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Rubens
 */
public class GameDao implements CrudOperations<Game> {

    @Override
    public Game persist(Game entity) throws SQLException {
        return null;
    }

    @Override
    public List<Game> findAll() throws SQLException {
        return null;
    }

    @Override
    public Game findById(Long id) throws SQLException {
        return null;
    }

    @Override
    public void delete(Long id) throws SQLException {

    }    
}