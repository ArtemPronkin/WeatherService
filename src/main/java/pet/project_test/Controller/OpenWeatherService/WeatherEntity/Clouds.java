package pet.project_test.Controller.OpenWeatherService.WeatherEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Clouds {
    Integer all;
}
