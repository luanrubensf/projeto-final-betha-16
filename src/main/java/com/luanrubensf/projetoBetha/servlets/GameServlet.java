package com.luanrubensf.projetoBetha.servlets;

import com.luanrubensf.projetoBetha.dao.GameDao;
import com.luanrubensf.projetoBetha.model.Game;
import com.luanrubensf.projetoBetha.utils.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

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

        GameDao dao = new GameDao();

        Game game = new Game();
        game.parse(Utils.parseReq2Map(request));

        try {
            game = dao.persist(game);
        } catch (Exception e) {
            response.sendError(400, e.getMessage());
            return;
        }

        response.setContentType("application/json");
        response.getWriter().append(game.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         if (Utils.isEmpty(req.getParameter("id"))) {
            resp.sendError(406, "Parâmetro ID obrigatório");
        } else {
            Long id = Utils.parseLong(req.getParameter("id"));
            GameDao dao = new GameDao();

            try {
                dao.delete(id);
            } catch (Exception e) {
                resp.sendError(400, e.getMessage());
            } 
        }
    }
}