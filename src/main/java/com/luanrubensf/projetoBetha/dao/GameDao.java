package com.luanrubensf.projetoBetha.dao;

import com.luanrubensf.projetoBetha.model.Game;
import com.luanrubensf.projetoBetha.utils.ConnectionUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rubens
 */
public class GameDao implements CrudOperations<Game> {

    private static final String INSERT = "INSERT INTO games (id, nome, descricao, idcategoria, ano, finalizado) values (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE games SET nome=?, descricao=?, idcategoria=?, ano=?, finalizado=? ";
    private static final String SELECT = "SELECT * FROM games";
    private static final String DELETE = "DELETE FROM games";
    private static final String WHEREID = " WHERE id=? ";
    private static final String SEQUENCE = "SELECT NEXTVAL('seqgames')";

    @Override
    public Game persist(Game entity) throws SQLException {
        return null;
    }

    @Override
    public List<Game> findAll() throws SQLException {
        List<Game> games = new ArrayList<>();

        Connection conn = ConnectionUtils.getConn();
        PreparedStatement pstm = conn.prepareStatement(SELECT);
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            games.add(parse(rs));
        }

        return games;
    }

    public Game parse(ResultSet rs) throws SQLException {
        Game game = new Game();
        CategoriaDao categoriaDao = new CategoriaDao();

        game.setId(rs.getLong("id"));
        game.setName(rs.getString("nome"));
        game.setDescricao(rs.getString("descricao"));
        game.setAnoLancamento(rs.getInt("ano"));
        game.setFinalizado(rs.getBoolean("finalizado"));
        game.setCategoria(categoriaDao.findById(rs.getLong("idcategoria")));
        
        return game;
    }

    @Override
    public Game findById(Long id) throws SQLException {
        return null;
    }

    @Override
    public void delete(Long id) throws SQLException {

    }
}
