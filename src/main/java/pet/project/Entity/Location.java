package pet.project.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int userId;
    private BigDecimal latitide;
    private BigDecimal longitide;

    public Location(String name, int userId, BigDecimal latitide, BigDecimal longitide) {
        this.name = name;
        this.userId = userId;
        this.latitide = latitide;
        this.longitide = longitide;
    }
}
