package pet.project_test.Controller.Service.OpenWeatherService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pet.project_test.Controller.Exception.ExceptionAPI.ExceptionOpenWeatherError;
import pet.project_test.Controller.Exception.ExceptionAPI.ExceptionRequestLimitExceeded;
import pet.project_test.Controller.Service.OpenWeatherService.WeatherEntity.LocationSearchDTO;
import pet.project_test.Controller.Service.OpenWeatherService.WeatherEntity.OpenWeatherLocationDTO;
import pet.project_test.Entity.Location.Location;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Slf4j
public class OpenWeatherApiService {
    private static final String APP_ID = System.getenv("OpenWeatherApiServiceId");
    private static final String WEATHER_API_URL = "https://api.openweathermap.org" + "/data/2.5/weather";
    private static final String GEOCODING_API_URL = "https://api.openweathermap.org" + "/geo/1.0/direct";
    private HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenWeatherApiService(HttpClient mockHttpClient) {
        httpClient = mockHttpClient;
    }

    public static HttpRequest buildRequest(URI uri) {
        return HttpRequest.newBuilder(uri)
                .GET()
                .build();
    }

    public OpenWeatherLocationDTO weatherForLocation(Location location) throws ExceptionRequestLimitExceeded, ExceptionOpenWeatherError {
        var uri = buildUriForWeatherForLocationRequest(location.getLatitide(), location.getLongitide());
        log.info(uri.toString());
        HttpRequest httpRequest = buildRequest(uri);
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            OpenWeatherLocationDTO dto = objectMapper.readValue(httpResponse.body(), new TypeReference<OpenWeatherLocationDTO>() {
            });
            if (dto.getCod() == 429) {
                throw new ExceptionRequestLimitExceeded("Request limit exceeded");
            }
            if (dto.getCod() != 200) {
                throw new ExceptionOpenWeatherError("OpenWeather server Error");
            }
            dto.setId(location.getId());
            dto.setLatitide(location.getLatitide());
            dto.setLongitide(location.getLongitide());
            return dto;
        } catch (IOException | InterruptedException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public List<LocationSearchDTO> geocodingByName(String name) {
        var uri = (buildUriForGeocodingRequest(name));
        log.info(uri.toString());
        HttpRequest httpRequest = buildRequest(uri);
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(httpResponse.body(), new TypeReference<List<LocationSearchDTO>>() {
            });
        } catch (IOException | InterruptedException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public URI buildUriForGeocodingRequest(String nameOfLocation) {
        return URI.create(GEOCODING_API_URL
                + "?q=" + nameOfLocation
                + "&limit=6"
                + "&appid=" + APP_ID);
    }

    public URI buildUriForWeatherForLocationRequest(BigDecimal lat, BigDecimal lon) {
        return URI.create(WEATHER_API_URL
                + "?lat=" + lat.toString()
                + "&lon=" + lon.toString()
                + "&units=metric"
                + "&appid=" + APP_ID);
    }

    public List<OpenWeatherLocationDTO> getListWeatherByListLocation(List<Location> list) throws ExceptionRequestLimitExceeded, ExceptionOpenWeatherError {
        List<OpenWeatherLocationDTO> result = new ArrayList<>();
        for (Location loc :
                list) {
            var dto = (weatherForLocation(loc));
            log.info(dto.getName());
            result.add(dto);
        }
        return result;
    }

}
