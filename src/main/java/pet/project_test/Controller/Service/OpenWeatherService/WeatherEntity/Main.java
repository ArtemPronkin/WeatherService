package pet.project_test.Controller.Service.OpenWeatherService.WeatherEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {

    Double temp;
    Double feels_like;
    Integer humidity;
}
