package pet.project_test.Entity.User;

import jakarta.persistence.*;
import lombok.*;
import pet.project_test.Entity.Location.Location;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", indexes = {@Index(name = "loginIndex", columnList = "login")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String login;
    private String password;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Location> locationList = new ArrayList<>();

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
