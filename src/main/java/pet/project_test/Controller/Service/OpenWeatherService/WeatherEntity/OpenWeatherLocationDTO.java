package pet.project_test.Controller.Service.OpenWeatherService.WeatherEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherLocationDTO {
    String name;
    Clouds clouds;
    Main main;
    Wind wind;
    Integer id;
    List<Weather> weather;
    Integer cod;
}
