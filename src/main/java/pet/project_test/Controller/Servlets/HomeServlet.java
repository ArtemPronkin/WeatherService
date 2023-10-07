package pet.project_test.Controller.Servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pet.project_test.Controller.Service.OpenWeatherService.WeatherEntity.OpenWeatherLocationDTO;
import pet.project_test.Entity.User.User;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends BaseServlet {

    @Override
    protected void get(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        webContext.setVariable("login", user.getLogin());
        List<OpenWeatherLocationDTO> weatherList = openWeatherApiService.getListWeatherByListLocation(user.getLocationList());
        webContext.setVariable("weatherList", weatherList);
        webContext.setVariable("login", user.getLogin());
        templateEngine.process("home", webContext, response.getWriter());
    }

    @Override
    protected void post(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        String id = request.getParameter("id");
        var location = user.getLocationList().get(Integer.parseInt(id));
        locationDAO.delete(location);
        response.sendRedirect(request.getContextPath() + request.getServletPath());
    }
}