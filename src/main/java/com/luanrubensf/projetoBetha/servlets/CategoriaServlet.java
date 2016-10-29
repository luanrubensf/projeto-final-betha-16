package com.luanrubensf.projetoBetha.servlets;

import com.luanrubensf.projetoBetha.dao.CategoriaDao;
import com.luanrubensf.projetoBetha.model.Categoria;
import com.luanrubensf.projetoBetha.utils.Utils;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rubens
 */
@WebServlet(name = "CategoriaServlet", urlPatterns = {"/api/categorias"})
public class CategoriaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CategoriaDao dao = new CategoriaDao();

        response.setContentType("application/json");

        try {
            if (Utils.isEmpty(request.getParameter("id"))) {
                response.getWriter().append(dao.findAll().toString());
            } else {
                Long id = Utils.parseLong(request.getParameter("id"));
                response.getWriter().append(dao.findById(id).toString());
            }
        } catch (Exception e) {
            setError(response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CategoriaDao dao = new CategoriaDao();

        Categoria categoria = new Categoria();
        categoria.parse(Utils.parseReq2Map(request));

        try {
            categoria = dao.persist(categoria);
        } catch (Exception e) {
            setError(response, e);
            return;
        }

        response.setContentType("application/json");
        response.getWriter().append(categoria.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Utils.isEmpty(req.getParameter("id"))) {
            resp.sendError(406, "Parâmetro ID obrigatório");
        } else {
            Long id = Utils.parseLong(req.getParameter("id"));
            CategoriaDao dao = new CategoriaDao();

            try {
                dao.delete(id);
            } catch (Exception e) {
                setError(resp, e);
            } 
        }
    }

    private void setError(HttpServletResponse response, Exception e) throws IOException {
        response.sendError(400, e.getMessage());
    }
}
