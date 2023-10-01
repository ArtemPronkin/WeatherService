package pet.project_test.Controller.OpenWeatherService.WeatherEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherLocationDTO {
    String name;
    Clouds clouds;
    Main main;
    Wind wind;
    Integer id;
}
