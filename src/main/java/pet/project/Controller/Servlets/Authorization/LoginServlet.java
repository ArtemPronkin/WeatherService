package pet.project.Controller.Servlets.Authorization;

import lombok.extern.slf4j.Slf4j;
import pet.project.Controller.Servlets.BaseServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pet.project.Entity.User;
import pet.project.Entity.UserDAO;
import pet.project.Exception.ExceptionUserAlreadyExists;

import java.io.IOException;
@Slf4j
@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("get login");
        templateEngine.process("login",webContext,response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("post login");
        response.sendRedirect(request.getContextPath()+"/home");
    }
}