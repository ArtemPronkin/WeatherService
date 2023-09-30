package pet.project_test.Entity.Location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class LocationSearchDTO {

    private String name;
    private BigDecimal lat;
    private BigDecimal lon;
    private String country;
    private String state;

}
