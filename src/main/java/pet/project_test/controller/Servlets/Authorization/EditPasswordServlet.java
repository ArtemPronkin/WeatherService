package pet.project_test.controller.Servlets.Authorization;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import pet.project_test.controller.Exception.ExceptionAuthorization.ExceptionWIthMessage;
import pet.project_test.controller.Servlets.BaseServlet;
import pet.project_test.controller.Validator;
import pet.project_test.entity.user.User;

import java.io.IOException;

@WebServlet(name = "EditPasswordServlet", value = "/edit")
public class EditPasswordServlet extends BaseServlet {
    @SneakyThrows
    @Override
    protected void get(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        templateEngine.process("editPassword", webContext, response.getWriter());
    }

    @SneakyThrows
    @Override
    protected void post(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {

        String password = request.getParameter("password");
        String newPassword = request.getParameter("newPassword");

        if (!Validator.isValidPassword(newPassword) || !Validator.isValidPassword(password)) {
            throw new ExceptionWIthMessage("Not valid");
        }
        userAccountService.login(user.getLogin(), password);
        userAccountService.editPassword(user, newPassword);
        response.sendRedirect(request.getContextPath() + "/home");
    }


}
