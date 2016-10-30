
package com.luanrubensf.projetoBetha.servlets;

import com.luanrubensf.projetoBetha.dao.GameDao;
import com.luanrubensf.projetoBetha.utils.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rubens
 */
@WebServlet(name = "GameServlet", urlPatterns = {"/api/games"})
public class GameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GameDao dao = new GameDao();

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
    
    }
}
