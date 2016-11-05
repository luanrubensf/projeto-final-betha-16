package com.luanrubensf.projetoBetha.servlets;

import com.luanrubensf.projetoBetha.dao.EmprestimoDao;
import com.luanrubensf.projetoBetha.model.Emprestimo;
import com.luanrubensf.projetoBetha.utils.Utils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rubens
 */
@WebServlet(name = "EmprestimoServlet", urlPatterns = {"/api/emprestimos"})
public class EmprestimoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EmprestimoDao dao = new EmprestimoDao();

        response.setContentType("application/json");

        try {
            if (Utils.isEmpty(request.getParameter("id"))) {
                response.getWriter().append(dao.findAll().toString());
            } else {
                Long id = Utils.parseLong(request.getParameter("id"));
                response.getWriter().append(dao.findById(id).toString());
            }
        } catch (Exception e) {
            response.sendError(400, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EmprestimoDao dao = new EmprestimoDao();

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.parse(Utils.parseReq2Map(request));

        try {
            emprestimo = dao.persist(emprestimo);
        } catch (Exception e) {
            response.sendError(400, e.getMessage());
            return;
        }

        response.setContentType("application/json");
        response.getWriter().append(emprestimo.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Utils.isEmpty(req.getParameter("id"))) {
            resp.sendError(406, "Parâmetro ID obrigatório");
        } else {
            Long id = Utils.parseLong(req.getParameter("id"));
            EmprestimoDao dao = new EmprestimoDao();

            try {
                dao.delete(id);
            } catch (Exception e) {
                resp.sendError(400, e.getMessage());
            }
        }
    }
}
