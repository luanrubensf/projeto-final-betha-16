package com.luanrubensf.projetoBetha.dao;

import com.luanrubensf.projetoBetha.model.Emprestimo;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Rubens
 */
public class EmprestimoDao implements CrudOperations<Emprestimo> {

    private static final String INSERT = "INSERT INTO emprestimos () values ()";
    private static final String UPDATE = "UPDATE emprestimos SET WHERE";
    private static final String SELECT = "SELECT * FROM emprestimos";
    private static final String DELETE = "DELETE FROM emprestimos";
    private static final String WHEREID = " WHERE id=? ";
    private static final String SEQUENCE = "SELECT NEXTVAL('seqemprestimos')";

    @Override
    public Emprestimo persist(Emprestimo entity) throws SQLException {
        return null;
    }

    @Override
    public List<Emprestimo> findAll() throws SQLException {
        return null;
    }

    @Override
    public Emprestimo findById(Long id) throws SQLException {
        return null;
    }

    @Override
    public void delete(Long id) throws SQLException {

    }
}