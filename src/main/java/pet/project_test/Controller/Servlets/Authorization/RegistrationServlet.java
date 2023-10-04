package pet.project_test.Controller.Servlets.Authorization;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pet.project_test.Controller.Exception.ExceptionWIthMessage;
import pet.project_test.Controller.Servlets.BaseServlet;
import pet.project_test.Controller.Validator;

import java.io.IOException;

@Slf4j
@WebServlet(name = "RegistrationServlet", value = "/registration")
public class RegistrationServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        templateEngine.process("registration", webContext, response.getWriter());
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (!Validator.isValidLogin(login) || !Validator.isValidPassword(password)) {
            throw new ExceptionWIthMessage("Not valid");
        }
        var user = userAccountService.registrationNewUser(login, password);
        var uuid = sessionUserService.createNewSession(user);
        sessionUserService.setSessionCookie(uuid, response);
        response.sendRedirect(request.getContextPath() + "/home");
    }
}
