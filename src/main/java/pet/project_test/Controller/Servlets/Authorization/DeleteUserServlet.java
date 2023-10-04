package pet.project_test.Controller.Servlets.Authorization;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pet.project_test.Controller.Exception.ExceptionWIthMessage;
import pet.project_test.Controller.Servlets.BaseServlet;
import pet.project_test.Controller.Validator;
import pet.project_test.Entity.User.User;

import java.io.IOException;

@WebServlet(name = "DeleteUserServlet", value = "/delete")
public class DeleteUserServlet extends BaseServlet {
    @Override
    protected void get(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        templateEngine.process("delete", webContext, response.getWriter());
    }

    @Override
    protected void post(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        String password = request.getParameter("password");

        if (!Validator.isValidPassword(password)) {
            throw new ExceptionWIthMessage("Not valid");
        }
        userAccountService.login(user.getLogin(), password);
        userAccountService.deleteUser(user);
        sessionUserService.setEmptyCookieSession(response);
        response.sendRedirect(request.getContextPath() + "/login" + "?message=Account delete");
    }


}
