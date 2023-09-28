package pet.project.Controller.Servlets.Authorization;

import lombok.extern.slf4j.Slf4j;
import pet.project.Controller.Servlets.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pet.project.Entity.Session;
import pet.project.Entity.SessionDAO;
import pet.project.Entity.User;
import pet.project.Entity.UserDAO;
import pet.project.Exception.ExceptionUserAlreadyExists;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@WebServlet(name = "RegistrationServlet", value = "/registration")
public class RegistrationServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("get reg");
        templateEngine.process("registration",webContext,response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("post reg");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        User user = new User(login,password);
        try {
            UserDAO.save(user);
        } catch (ExceptionUserAlreadyExists e) {
            throw new RuntimeException(e);
        }
        Session session = new Session(UUID.randomUUID(), user, LocalDateTime.now().plusHours(24));
        SessionDAO.save(session);
        response.sendRedirect(request.getContextPath()+"/home");
    }
}
