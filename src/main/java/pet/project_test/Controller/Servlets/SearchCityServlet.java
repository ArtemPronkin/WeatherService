package pet.project_test.Controller.Servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pet.project_test.Controller.OpenWeatherService.OpenWeatherApiService;
import pet.project_test.Controller.Servlets.Authorization.LoginFilter;
import pet.project_test.Controller.Validator;
import pet.project_test.Entity.Location.Location;
import pet.project_test.Entity.Location.LocationDAO;
import pet.project_test.Entity.Location.LocationSearchDTO;
import pet.project_test.Entity.Session.Session;
import pet.project_test.Entity.Session.SessionDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "SearchCityServlet", value = "/search")
public class SearchCityServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var uuid = LoginFilter.getSessionUUIDFromRequest(request).get();
        Optional<Session> optionalSession = SessionDAO.getById(uuid);
        var user = optionalSession.get().getUser();
        webContext.setVariable("login", user.getLogin());

        String name = request.getParameter("name");
        if (name == null) {
            name = "";
        }
        List<LocationSearchDTO> locationsList = OpenWeatherApiService.geocodingFromName(name.replace(' ', '+'));
        webContext.setVariable("locationsList", locationsList);
        templateEngine.process("search", webContext, response.getWriter());


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        var uuid = LoginFilter.getSessionUUIDFromRequest(request).get();
        Optional<Session> optionalSession = SessionDAO.getById(uuid);
        var user = optionalSession.get().getUser();
        Location location = new Location(user, name, new BigDecimal(longitude), new BigDecimal(latitude));
        LocationDAO.save(location);
        response.sendRedirect(request.getContextPath() + "/home");

    }
}