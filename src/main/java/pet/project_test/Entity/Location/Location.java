package pet.project_test.Entity.Location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @Column(precision = 38, scale = 10)
    private BigDecimal latitide;
    @Column(precision = 38, scale = 10)
    private BigDecimal longitide;

    public Location(User user, String name, BigDecimal latitide, BigDecimal longitide) {
        this.name = name;
        this.latitide = latitide;
        this.longitide = longitide;
        this.user = user;
    }
}
