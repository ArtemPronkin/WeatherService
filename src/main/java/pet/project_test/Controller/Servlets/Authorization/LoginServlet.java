package pet.project_test.Controller.Servlets.Authorization;

import com.password4j.Password;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pet.project_test.Controller.Exception.ExceptionWIthMessage;
import pet.project_test.Controller.Servlets.BaseServlet;
import pet.project_test.Controller.Validator;
import pet.project_test.Entity.Session.Session;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends BaseServlet {


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        templateEngine.process("login", webContext, response.getWriter());

    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (!Validator.isValidLogin(login) || !Validator.isValidPassword(password)) {
            throw new ExceptionWIthMessage("Not valid");
        }

        var user = userDAO.findByLogin(login);
        if (user.isEmpty()) {
            throw new ExceptionWIthMessage("User not found");
        }
        var check = Password.check(password, user.get().getPassword()).withBcrypt();
        if (!check) {
            throw new ExceptionWIthMessage("Incorrect password");
        }

        Session session = new Session(UUID.randomUUID(), user.get(), LocalDateTime.now().plusHours(24));
        sessionDAO.save(session);
        Cookie cookie = new Cookie("sessionId", session.getId().toString());
        log.info("add Cookie: " + session.getId().toString());
        response.addCookie(cookie);

        response.sendRedirect(request.getContextPath() + "/home");
    }
}