package pet.project.Controller.Servlets.Authorization;

import jakarta.servlet.http.Cookie;
import pet.project.Controller.Servlets.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet(name = "OutProfileServlet", value = "/out")
public class OutProfileServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie emptyCookie = new Cookie("sessionId", null);
        emptyCookie.setMaxAge(0);
        response.addCookie(emptyCookie);
        response.sendRedirect(request.getContextPath()+"/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
