package pet.project_test.Controller.Servlets.Authorization;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import pet.project_test.Controller.Servlets.BaseServlet;
import pet.project_test.Entity.User.User;

import java.io.IOException;

@WebServlet(name = "OutProfileServlet", value = "/out")
public class OutProfileServlet extends BaseServlet {
    @Override
    @SneakyThrows
    protected void get(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        sessionUserService.deleteSession(request);
        sessionUserService.setEmptyCookieSession(response);
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    protected void post(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {

    }
}
