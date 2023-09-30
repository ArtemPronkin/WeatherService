import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pet.project_test.Entity.User.User;
import pet.project_test.Entity.User.UserDAO;
import pet.project_test.Exception.ExceptionUserAlreadyExists;

public class SessionTest {

    @Test
    public void uniqueUserThrowTest() throws ExceptionUserAlreadyExists {
        var user1 = new User("test@test","password");
        var user2 = new User("test@test","22password22");
        UserDAO.save(user1);
        Assertions.assertThrows(ExceptionUserAlreadyExists.class,()-> UserDAO.save(user2));
    }

}
