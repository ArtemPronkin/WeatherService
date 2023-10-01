package pet.project_test.Controller.Servlets;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pet.project_test.Controller.OpenWeatherService.OpenWeatherApiService;
import pet.project_test.Controller.OpenWeatherService.WeatherEntity.OpenWeatherLocationDTO;
import pet.project_test.Controller.Servlets.Authorization.LoginFilter;
import pet.project_test.Entity.Location.Location;
import pet.project_test.Entity.Location.LocationDAO;
import pet.project_test.Entity.Location.LocationSearchDTO;
import pet.project_test.Entity.Session.Session;
import pet.project_test.Entity.Session.SessionDAO;
import pet.project_test.Entity.User.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends BaseServlet {
    protected void get(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        webContext.setVariable("login", user.getLogin());
        List<OpenWeatherLocationDTO> weatherList = OpenWeatherApiService.getLocationForUser(user.getLocationList());
        webContext.setVariable("weatherList", weatherList);
        webContext.setVariable("login", user.getLogin());
        templateEngine.process("home", webContext, response.getWriter());
    }

    @Override
    protected void post(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        String id = request.getParameter("id");
        LocationDAO.deleteForId(Integer.parseInt(id));
        doGet(request,response);
    }
}