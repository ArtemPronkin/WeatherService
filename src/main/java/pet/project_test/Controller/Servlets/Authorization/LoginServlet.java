package pet.project_test.Controller.Servlets.Authorization;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pet.project_test.Controller.Exception.ExceptionWIthMessage;
import pet.project_test.Controller.Servlets.BaseServlet;
import pet.project_test.Controller.Validator;
import pet.project_test.Entity.User.User;

import java.io.IOException;

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
        User user = userAccountService.login(login, password);
        var uuid = sessionUserService.createNewSession(user);
        sessionUserService.addSessionCookie(uuid, response);
        response.sendRedirect(request.getContextPath() + "/home");
    }
}