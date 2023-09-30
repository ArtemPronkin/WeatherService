package pet.project_test.Entity.Location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.ws.rs.ext.ParamConverter;
import lombok.*;
import pet.project_test.Entity.User.User;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    private BigDecimal latitide;
    private BigDecimal longitide;

    public Location(User user,String name, BigDecimal latitide, BigDecimal longitide) {
        this.name = name;
        this.latitide = latitide;
        this.longitide = longitide;
        this.user = user;
    }
}
