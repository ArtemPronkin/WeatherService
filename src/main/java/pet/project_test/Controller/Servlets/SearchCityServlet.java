package pet.project_test.Controller.Servlets;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pet.project_test.Entity.Location.Location;
import pet.project_test.Controller.OpenWeatherService.WeatherEntity.LocationSearchDTO;
import pet.project_test.Entity.User.User;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@WebServlet(name = "SearchCityServlet", value = "/search")
public class SearchCityServlet extends BaseServlet {

    protected void get(HttpServletRequest request, HttpServletResponse response, User user) throws  IOException {
        webContext.setVariable("login", user.getLogin());

        String name = request.getParameter("name");
        if (name == null) {
            name = "";
        }
        List<LocationSearchDTO> locationsList = openWeatherApiService.geocodingFromName(name.replace(' ', '+'));
        webContext.setVariable("locationsList", locationsList);
        templateEngine.process("search", webContext, response.getWriter());


    }

    @Override
    protected void post(HttpServletRequest request, HttpServletResponse response, User user) throws  IOException {

        String name = request.getParameter("name");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");;

        var bigDecimallat = new BigDecimal(latitude).setScale(10, RoundingMode.HALF_DOWN);
        var bigDecimallon = new BigDecimal(longitude).setScale(10, RoundingMode.HALF_DOWN);

        for (Location loc: user.getLocationList()) {
            if (loc.getLongitide().setScale(10, RoundingMode.HALF_DOWN)
                    .equals(bigDecimallon)
                    && loc.getLatitide().setScale(10, RoundingMode.HALF_DOWN)
                    .equals(bigDecimallat)){
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }
        }

        Location location = new Location(user, name, new BigDecimal(latitude), new BigDecimal(longitude));
        locationDAO.save(location);
        response.sendRedirect(request.getContextPath() + "/home");

    }
}