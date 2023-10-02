package pet.project_test.Controller.Servlets;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pet.project_test.Controller.OpenWeatherService.WeatherEntity.OpenWeatherLocationDTO;
import pet.project_test.Entity.User.User;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends BaseServlet {
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
        locationDAO.deleteForId(Integer.parseInt(id));
        doGet(request,response);
    }
}