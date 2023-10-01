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
import pet.project_test.Entity.User.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "SearchCityServlet", value = "/search")
public class SearchCityServlet extends BaseServlet {

    protected void get(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
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
    protected void post(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {

        String name = request.getParameter("name");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");;
        Location location = new Location(user, name, new BigDecimal(longitude), new BigDecimal(latitude));
        LocationDAO.save(location);
        response.sendRedirect(request.getContextPath() + "/home");

    }
}