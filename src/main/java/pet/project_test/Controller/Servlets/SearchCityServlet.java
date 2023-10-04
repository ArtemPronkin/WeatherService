package pet.project_test.Controller.Servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import pet.project_test.Controller.Exception.ExceptionEmptyListFound;
import pet.project_test.Controller.Exception.ExceptionLocationAlreadyExist;
import pet.project_test.Controller.Service.OpenWeatherService.WeatherEntity.LocationSearchDTO;
import pet.project_test.Entity.Location.Location;
import pet.project_test.Entity.User.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@WebServlet(name = "SearchCityServlet", value = "/search")
public class SearchCityServlet extends BaseServlet {

    @SneakyThrows
    @Override
    protected void get(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
        webContext.setVariable("login", user.getLogin());

        String name = request.getParameter("name");
        if (name == null || name.isBlank()) {
            throw new ExceptionEmptyListFound("Empty search blank");
        }

        List<LocationSearchDTO> locationsList = openWeatherApiService.geocodingByName(name.replace(' ', '+'));
        if (locationsList.isEmpty()) {
            throw new ExceptionEmptyListFound("Location not found");
        }
        webContext.setVariable("locationsList", locationsList);
        templateEngine.process("search", webContext, response.getWriter());


    }

    @SneakyThrows
    @Override
    protected void post(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {

        String name = request.getParameter("name");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        ;

        var bigDecimalLat = new BigDecimal(latitude).setScale(10, RoundingMode.HALF_DOWN);
        var bigDecimalLon = new BigDecimal(longitude).setScale(10, RoundingMode.HALF_DOWN);

        for (Location loc : user.getLocationList()) {
            if (loc.getLongitide().setScale(10, RoundingMode.HALF_DOWN)
                    .equals(bigDecimalLon)
                    && loc.getLatitide().setScale(10, RoundingMode.HALF_DOWN)
                    .equals(bigDecimalLat)) {
                throw new ExceptionLocationAlreadyExist("Location already exists in home");
            }
        }

        Location location = new Location(user, name, new BigDecimal(latitude), new BigDecimal(longitude));
        locationDAO.save(location);
        response.sendRedirect(request.getContextPath() + "/home");

    }
}