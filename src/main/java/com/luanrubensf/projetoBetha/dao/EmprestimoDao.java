package com.luanrubensf.projetoBetha.dao;

import com.luanrubensf.projetoBetha.model.Emprestimo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rubens
 */
public class EmprestimoDao implements CrudOperations<Emprestimo> {

    private static final String INSERT = "INSERT INTO emprestimos (id, destino, emissao, devolucao, observacao, idgame)  values (?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE emprestimos SET destino= ?, emissao=?, devolucao=?, observacao=?, idgame=? ";
    private static final String SELECT = "SELECT * FROM emprestimos";
    private static final String DELETE = "DELETE FROM emprestimos";
    private static final String WHEREID = " WHERE id=? ";
    private static final String SEQUENCE = "SELECT NEXTVAL('seqemprestimos')";

    @Override
    public Emprestimo persist(Emprestimo entity) throws Exception {
        Emprestimo emprestimo;
        if (entity.getId() == null) {
            emprestimo = insert(entity);
        } else {
            emprestimo = update(entity);
        }

        return emprestimo;
    }

    @Override
    public List<Emprestimo> findAll() throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();

        Connection conn = ConnectionUtils.getConn();
        PreparedStatement pstm = conn.prepareStatement(SELECT);

        try {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                emprestimos.add(parse(rs));
            }
            return emprestimos;

        } finally {
            conn.close();
        }
    }

    @Override
    public Emprestimo findById(Long id) throws SQLException {
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

    private Emprestimo parse(ResultSet rs) throws SQLException {
        Emprestimo emprestimo = new Emprestimo();
        GameDao gameDao = new GameDao();

        emprestimo.setId(rs.getLong("id"));
        emprestimo.setDestino(rs.getString("destino"));
        emprestimo.setObservacao(rs.getString("observacao"));
        emprestimo.setGame(gameDao.findById(rs.getLong("idgame")));
        emprestimo.setEmissao(rs.getTimestamp("emissao") == null ? null : rs.getTimestamp("emissao").toLocalDateTime());
        emprestimo.setDevolucao(rs.getTimestamp("devolucao") == null ? null : rs.getTimestamp("devolucao").toLocalDateTime());

        return emprestimo;
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

    private Emprestimo insert(Emprestimo entity) throws Exception {
        entity.setId(nextId());
        Connection conn = ConnectionUtils.getConn();
        PreparedStatement pstm = conn.prepareStatement(INSERT);

        try {
            pstm.setLong(1, entity.getId());
            pstm.setString(2, entity.getDestino());
            pstm.setTimestamp(3, Timestamp.valueOf(entity.getEmissao() == null ? LocalDateTime.now() : entity.getEmissao()));
            pstm.setTimestamp(4, Timestamp.valueOf(entity.getDevolucao()));
            pstm.setString(5, entity.getObservacao());
            pstm.setObject(6, entity.getGame().getId());

            pstm.execute();

            return findById(entity.getId());

        } finally {
            conn.close();
        }
    }

    private Emprestimo update(Emprestimo entity) throws Exception {
        Connection conn = ConnectionUtils.getConn();
        PreparedStatement pstm = conn.prepareStatement(UPDATE + WHEREID);

        try {
            pstm.setString(1, entity.getDestino());
            pstm.setTimestamp(2, Timestamp.valueOf(entity.getEmissao() == null ? LocalDateTime.now() : entity.getEmissao()));
            pstm.setTimestamp(3, Timestamp.valueOf(entity.getDevolucao()));
            pstm.setString(4, entity.getObservacao());
            pstm.setObject(5, entity.getGame().getId());

            pstm.setLong(6, entity.getId());

            pstm.execute();

            return findById(entity.getId());
        } finally {
            conn.close();
        }
    }
}
