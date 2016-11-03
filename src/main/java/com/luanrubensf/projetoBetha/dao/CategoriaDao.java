package com.luanrubensf.projetoBetha.dao;

import com.luanrubensf.projetoBetha.model.Categoria;
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
public class CategoriaDao implements CrudOperations<Categoria> {

    private static final String INSERT = "INSERT INTO categorias (id, descricao) values (?, ?)";
    private static final String UPDATE = "UPDATE categorias SET descricao=? ";
    private static final String SELECT = "SELECT id, descricao FROM categorias";
    private static final String DELETE = "DELETE FROM categorias";
    private static final String WHEREID = " WHERE id=? ";
    private static final String SEQUENCE = "SELECT NEXTVAL('seqcategorias')";

    private Categoria insert(Categoria entity) throws SQLException {
        entity.setId(nextId());
        Connection conn = ConnectionUtils.getConn();
        PreparedStatement pstm = conn.prepareStatement(INSERT);
        try {
            pstm.setLong(1, entity.getId());
            pstm.setString(2, entity.getDescricao());
            pstm.execute();
        } finally {
            conn.close();
        }
        return findById(entity.getId());
    }

    private Categoria update(Categoria entity) throws SQLException {
        Connection conn = ConnectionUtils.getConn();
        PreparedStatement stm = conn.prepareStatement(UPDATE + WHEREID);

        try {
            stm.setString(1, entity.getDescricao());
            stm.setLong(2, entity.getId());
            stm.execute();

        } finally {
            conn.close();
        }

        return findById(entity.getId());
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

    @Override
    public Categoria persist(Categoria entity) throws SQLException {
        Categoria categoria;
        if (entity.getId() == null) {
            categoria = insert(entity);
        } else {
            categoria = update(entity);
        }

        return categoria;
    }

    @Override
    public List<Categoria> findAll() throws SQLException {

        List<Categoria> registros = new ArrayList<>();

        Connection conn = ConnectionUtils.getConn();
        PreparedStatement stm = conn.prepareStatement(SELECT);

        try {
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                registros.add(parse(rs));
            }
        } finally {
            conn.close();
        }

        return registros;
    }

    private Categoria parse(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("id"));
        categoria.setDescricao(rs.getString("descricao"));

        return categoria;
    }

    @Override
    public Categoria findById(Long id) throws SQLException {
        Connection conn = ConnectionUtils.getConn();
        PreparedStatement stm = conn.prepareStatement(SELECT + WHEREID);

        try {
            stm.setLong(1, id);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return parse(rs);
            }
        } finally {
            conn.close();
        }
        return null;
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
}
