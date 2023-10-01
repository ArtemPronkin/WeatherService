import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pet.project_test.Entity.User.User;
import pet.project_test.Entity.User.UserDAO;

public class SessionTest {
    UserDAO userDAO = new UserDAO();

    @Test
    public void uniqueUserThrowTest(){
        var user1 = new User("test@test","password");
        var user2 = new User("test@test","22password22");
        userDAO.save(user1);
        Assertions.assertThrows(PersistenceException.class,()-> userDAO.save(user2));
    }

}
