package pet.project_test.Entity.Session;

import jakarta.persistence.*;
import lombok.*;
import pet.project_test.Entity.User.User;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@EqualsAndHashCode(exclude = "user")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sessions")
public class Session {
    @Id
    private UUID id;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "expires_at")
    private LocalDateTime ExpiresAt;
}
