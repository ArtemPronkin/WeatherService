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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        var uuid = LoginFilter.getSessionUUIDFromRequest(request).get();
        Optional<Session> optionalSession = SessionDAO.getById(uuid);
        var user = optionalSession.get().getUser();
        webContext.setVariable("login", user.getLogin());
        List<OpenWeatherLocationDTO> weatherList = OpenWeatherApiService.getLocationForUser(user.getLocationList());
        webContext.setVariable("weatherList", weatherList);
        webContext.setVariable("login", user.getLogin());
        templateEngine.process("home", webContext, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        var uuid = LoginFilter.getSessionUUIDFromRequest(request).get();
        Optional<Session> optionalSession = SessionDAO.getById(uuid);
        var user = optionalSession.get().getUser();

        LocationDAO.deleteForId(Integer.parseInt(id));
        doGet(request,response);

    }
}