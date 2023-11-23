package pet.project_test.entity.session;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pet.project_test.entity.user.User;

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
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @Column(name = "expires_at")
    private LocalDateTime ExpiresAt;
}
