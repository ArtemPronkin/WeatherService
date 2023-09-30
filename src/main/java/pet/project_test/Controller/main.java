package pet.project_test.Controller;

import pet.project_test.Entity.Session.Session;
import pet.project_test.Entity.Session.SessionDAO;
import pet.project_test.Entity.User.User;

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
