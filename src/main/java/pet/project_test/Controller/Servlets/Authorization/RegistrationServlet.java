package pet.project_test.Controller.Servlets.Authorization;

import com.password4j.Password;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import pet.project_test.Controller.Servlets.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pet.project_test.Controller.Validator;
import pet.project_test.Entity.Session.Session;
import pet.project_test.Entity.Session.SessionDAO;
import pet.project_test.Entity.User.User;
import pet.project_test.Entity.User.UserDAO;
import pet.project_test.Exception.ExceptionUserAlreadyExists;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@WebServlet(name = "RegistrationServlet", value = "/registration")
public class RegistrationServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        webContext.setVariable("message",request.getParameter("message"));
        templateEngine.process("registration",webContext,response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (! Validator.isValidLogin(login) || ! Validator.isValidPassword(password)){
            response.sendRedirect(request.getContextPath()+"/registration?message="+"Not valid" );
            return;
        }


        var passwordBcrypt = Password.hash(password).withBcrypt().getResult();
        User user = new User(login,passwordBcrypt);
        try {
            UserDAO.save(user);
        } catch (ExceptionUserAlreadyExists e) {
            response.sendRedirect(request.getContextPath()+"/registration?message="+"User Already Exists" );
            return;
        }
        Session session = new Session(UUID.randomUUID(), user, LocalDateTime.now().plusHours(24));
        SessionDAO.save(session);
        Cookie cookie = new Cookie("sessionId", session.getId().toString());
        log.info("add Cookie: " + session.getId().toString());
        response.addCookie(cookie);
        response.sendRedirect(request.getContextPath()+"/home");
    }
}
