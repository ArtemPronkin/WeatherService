package pet.project.Controller;

import pet.project.Entity.Session;
import pet.project.Entity.SessionDAO;
import pet.project.Entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class main {
    public static void main(String[] args) {
        User user = new User("login","password");
        var uuid = UUID.randomUUID();
        Session session = new Session(uuid, user, LocalDateTime.now().plusHours(24));
        SessionDAO.save(session);

    }
}
