package pet.project.Controller.Servlets;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pet.project.Controller.Servlets.Authorization.LoginFilter;
import pet.project.Entity.Session;
import pet.project.Entity.SessionDAO;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Session> optionalSession = SessionDAO.getById(LoginFilter.getSessionUUIDFromRequest(request).get());
        webContext.setVariable("login", optionalSession.get().getUser().getLogin());
            templateEngine.process("home",webContext,response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}