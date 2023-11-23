package pet.project_test.controller.Service.OpenWeatherService.WeatherEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LocationSearchDTO {

    private String name;
    private BigDecimal lat;
    private BigDecimal lon;
    private String country;
    private String state;

}
