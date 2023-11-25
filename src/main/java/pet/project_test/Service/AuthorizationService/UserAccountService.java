package pet.project_test.Service.AuthorizationService;

import com.password4j.Password;
import jakarta.persistence.PersistenceException;
import pet.project_test.Exception.ExceptionAuthorization.ExceptionIncorrectPassword;
import pet.project_test.Exception.ExceptionAuthorization.ExceptionUserAlreadyExistsException;
import pet.project_test.Exception.ExceptionAuthorization.ExceptionUserNotFound;
import pet.project_test.entity.location.Location;
import pet.project_test.entity.location.LocationDAO;
import pet.project_test.entity.user.User;
import pet.project_test.entity.user.UserDAO;

import java.math.BigDecimal;

public class UserAccountService {
    UserDAO userDAO = new UserDAO();
    LocationDAO locationDAO = new LocationDAO();

    public void saveLocation(Location location) {
        locationDAO.save(location);
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
            throw new ExceptionIncorrectPassword("Incorrect     password");
        }
        return user.get();
    }

    public void deleteUserLocation(User user, BigDecimal lat, BigDecimal lon) {
        locationDAO.deleteByUser(user, lat, lon);
    }
}
