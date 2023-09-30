package pet.project_test.Controller.OpenWeatherService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import pet.project_test.Controller.OpenWeatherService.WeatherEntity.OpenWeatherLocationDTO;
import pet.project_test.Controller.Servlets.BaseServlet;
import pet.project_test.Entity.Location.Location;
import pet.project_test.Entity.Location.LocationSearchDTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OpenWeatherApiService {
    private static final String APP_ID = "4c31277b10243af9ff3161584e3a4a5c";
    private static final String WEATHER_API_URL = "https://api.openweathermap.org" + "/data/2.5/weather";
    private static final String GEOCODING_API_URL = "https://api.openweathermap.org" + "/geo/1.0/direct";

    private static final HttpClient client = HttpClient.newHttpClient();
    public static ObjectMapper objectMapper = new ObjectMapper();

    private static HttpRequest buildRequest(URI uri) {
        return HttpRequest.newBuilder(uri)
                .GET()
                .build();
    }

    public static OpenWeatherLocationDTO weatherForLocation(Location location) {
        var uri = buildUriForWeatherForLocationRequest(location.getLatitide(), location.getLongitide());
        log.info(uri.toString());
        HttpRequest httpRequest = buildRequest(uri);
        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(httpResponse.body(), new TypeReference<OpenWeatherLocationDTO>() {});
        } catch (IOException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static List<LocationSearchDTO> geocodingFromName(String name) {
        var uri = (buildUriForGeocodingRequest(name));
        log.info(uri.toString());
        HttpRequest httpRequest = buildRequest(uri);
        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(httpResponse.body(), new TypeReference<List<LocationSearchDTO>>() {
            });
        } catch (IOException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private static URI buildUriForGeocodingRequest(String nameOfLocation) {
        // Somehow without explicit limit api returns only 1 object
        return URI.create(GEOCODING_API_URL
                + "?q=" + nameOfLocation
                + "&limit=6"
                + "&appid=" + APP_ID);
    }

    private static URI buildUriForWeatherForLocationRequest(BigDecimal lat, BigDecimal lon) {
        // Somehow without explicit limit api returns only 1 object
        return URI.create(WEATHER_API_URL
                + "?lat=" + lat.toString()
                + "&lon=" + lon.toString()
                + "&units=metric"
                + "&appid=" + APP_ID);
    }

    public static List<OpenWeatherLocationDTO> getLocationForUser(List<Location> list) {
        List<OpenWeatherLocationDTO> result = new ArrayList<>();
        for (Location loc :
                list) {
            var dto = (weatherForLocation(loc));
            log.info(dto.getName());
            dto.setId(loc.getId());
            result.add(dto);
        }
        return result;
    }

}
