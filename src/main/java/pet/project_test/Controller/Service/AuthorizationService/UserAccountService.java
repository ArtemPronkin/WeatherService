package pet.project_test.Controller.Service.AuthorizationService;

import com.password4j.Password;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import pet.project_test.Controller.Exception.ExceptionAuthorization.ExceptionAccess;
import pet.project_test.Controller.Exception.ExceptionAuthorization.ExceptionIncorrectPassword;
import pet.project_test.Controller.Exception.ExceptionAuthorization.ExceptionUserAlreadyExistsException;
import pet.project_test.Controller.Exception.ExceptionAuthorization.ExceptionUserNotFound;
import pet.project_test.Entity.Session.Session;
import pet.project_test.Entity.Session.SessionDAO;
import pet.project_test.Entity.User.User;
import pet.project_test.Entity.User.UserDAO;

import java.util.Optional;

public class UserAccountService {
    UserDAO userDAO = new UserDAO();
    SessionDAO sessionDAO = new SessionDAO();
    SessionUserService sessionUserService = new SessionUserService();


    public User getUser(HttpServletRequest request) throws ExceptionAccess {
        var uuid = sessionUserService.getSessionUUIDFromRequest(request).orElseThrow(() -> new ExceptionAccess("Session not found"));
        Optional<Session> optionalSession = sessionDAO.getById(uuid);
        return optionalSession.orElseThrow(() -> new ExceptionAccess("User not found")).getUser();
    }

    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    public void editPassword(User user, String password) {
        var passwordBcrypt = Password.hash(password).withBcrypt().getResult();
        user.setPassword(passwordBcrypt);
        userDAO.update(user);
    }

    public User registrationNewUser(String login, String password) throws ExceptionUserAlreadyExistsException {
        var passwordBcrypt = Password.hash(password).withBcrypt().getResult();
        User user = new User(login, passwordBcrypt);
        try {
            userDAO.save(user);
        } catch (PersistenceException e) {
            throw new ExceptionUserAlreadyExistsException("User already exist");
        }
        return user;
    }

    public User login(String login, String password) throws ExceptionUserNotFound, ExceptionIncorrectPassword {
        var user = userDAO.findByLogin(login);
        if (user.isEmpty()) {
            throw new ExceptionUserNotFound("User is not registration");
        }
        var check = Password.check(password, user.get().getPassword()).withBcrypt();
        if (!check) {
            throw new ExceptionIncorrectPassword("Incorrect password");
        }
        return user.get();
    }
}
