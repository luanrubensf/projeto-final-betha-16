package com.luanrubensf.projetoBetha.dao;

import com.luanrubensf.projetoBetha.model.Game;
import com.luanrubensf.projetoBetha.utils.ConnectionUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private static final String WHERE_FINALIZADOS = "WHERE ifnalizado = ?";
    private static final String SEQUENCE = "SELECT NEXTVAL('seqgames')";

    @Override
    public Game persist(Game entity) throws SQLException {
        Game game;
        if (entity.getId() == null) {
            game = insert(entity);
        } else {
            game = update(entity);
        }

        return game;
    }

    @Override
    public List<Game> findAll() throws SQLException {
        List<Game> games = new ArrayList<>();

        Connection conn = ConnectionUtils.getConn();
        PreparedStatement pstm = conn.prepareStatement(SELECT);

        try {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                games.add(parse(rs));
            }
            return games;

        } finally {
            conn.close();
        }
    }

    public Game parse(ResultSet rs) throws SQLException {
        Game game = new Game();
        CategoriaDao categoriaDao = new CategoriaDao();

        game.setId(rs.getLong("id"));
        game.setNome(rs.getString("nome"));
        game.setDescricao(rs.getString("descricao"));
        
        Integer ano = (Integer) rs.getObject("ano");
        
        game.setAnoLancamento(ano);
        game.setFinalizado(rs.getBoolean("finalizado"));
        game.setCategoria(categoriaDao.findById(rs.getLong("idcategoria")));

        return game;
    }

    @Override
    public Game findById(Long id) throws SQLException {
        Connection conn = ConnectionUtils.getConn();
        PreparedStatement pstm = conn.prepareStatement(SELECT + WHEREID);
        try {
            pstm.setLong(1, id);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return parse(rs);
            }
            return null;
        } finally {
            conn.close();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        Connection conn = ConnectionUtils.getConn();
        PreparedStatement stm = conn.prepareStatement(DELETE + WHEREID);
        try {
            stm.setLong(1, id);
            stm.execute();
        } finally {
            conn.close();
        }
    }

    public List<Game> findFinalizados(Boolean isFinalizado) throws SQLException {
        List<Game> games = new ArrayList<>();

        Connection conn = ConnectionUtils.getConn();
        PreparedStatement pstm = conn.prepareStatement(SELECT + WHERE_FINALIZADOS);

        try {
            pstm.setBoolean(1, isFinalizado);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                games.add(parse(rs));
            }
            return games;
        } finally {
            conn.close();
        }
    }

    private Long nextId() throws SQLException {
        Connection conn = ConnectionUtils.getConn();
        Statement stm = conn.createStatement();

        try {
            ResultSet rs = stm.executeQuery(SEQUENCE);
            rs.next();
            return rs.getLong(1);
        } finally {
            conn.close();
        }
    }

    private Game insert(Game entity) throws SQLException {
        entity.setId(nextId());
        Connection conn = ConnectionUtils.getConn();
        PreparedStatement pstm = conn.prepareStatement(INSERT);

        try {
            pstm.setLong(1, entity.getId());
            pstm.setString(2, entity.getNome());
            pstm.setString(3, entity.getDescricao());

            pstm.setObject(4, entity.getCategoria() != null ? entity.getCategoria().getId() : null);

            pstm.setObject(5, entity.getAnoLancamento());
            pstm.setBoolean(6, entity.isFinalizado());

            pstm.execute();

            return findById(entity.getId());
        } finally {
            conn.close();
        }
    }

    private Game update(Game entity) throws SQLException {
        Connection conn = ConnectionUtils.getConn();
        PreparedStatement stm = conn.prepareStatement(UPDATE + WHEREID);

        try {
            stm.setString(1, entity.getNome());
            stm.setString(2, entity.getDescricao());

            stm.setObject(3, entity.getCategoria() != null ? entity.getCategoria().getId() : null);

            stm.setObject(4, entity.getAnoLancamento());
            stm.setBoolean(5, entity.isFinalizado());

            stm.setLong(6, entity.getId());

            stm.execute();

            return findById(entity.getId());
        } finally {
            conn.close();
        }
    }
}
