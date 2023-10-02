import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pet.project_test.Controller.Servlets.Authorization.LoginFilter;
import pet.project_test.Entity.Session.Session;
import pet.project_test.Entity.Session.SessionDAO;
import pet.project_test.Entity.User.User;
import pet.project_test.Entity.User.UserDAO;

import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SessionTest {
    static UserDAO userDAO = new UserDAO();
    static SessionDAO sessionDAO = new SessionDAO();
    static LoginFilter loginFilter = new LoginFilter();

    @Test
    public void uniqueUserThrowTest() {
        var user1 = new User("test@test", "password");
        var user2 = new User("test@test", "22password22");
        userDAO.save(user1);
        Assertions.assertThrows(PersistenceException.class, () -> userDAO.save(user2));
    }
    @Test
    public void isActualSessionWithEmptyCookieTest(){
        var cookies = new Cookie[0];
        var httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        Assertions.assertEquals(false,loginFilter.itsActualSession(httpServletRequest));
    }

    @Test
    public void isActualSessionWithRandomUUIDCookieTest(){
        var user1 = new User("testCockie", "password");
        userDAO.save(user1);
        var cookies = new Cookie[]{new Cookie("sessionId",UUID.randomUUID().toString())};
        var httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getCookies()).thenReturn(cookies);

        Assertions.assertEquals(false,loginFilter.itsActualSession(httpServletRequest));
    }
    @Test
    public void isActualSessionWithExpiredSessionCookieTest(){
        UUID uuid = UUID.randomUUID();
        var user1 = new User("testCockie1", "password");
        userDAO.save(user1);
        sessionDAO.save(new Session(uuid,user1,LocalDateTime.now().minusDays(1)));
        var cookies = new Cookie[]{new Cookie("sessionId",uuid.toString())};
        var httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        Assertions.assertEquals(false,loginFilter.itsActualSession(httpServletRequest));
    }
    @Test
    public void isActualSessionWithActualSessionCookieTest(){
        UUID uuid = UUID.randomUUID();
        var user1 = new User("testCockie2", "password");
        userDAO.save(user1);
        sessionDAO.save(new Session(uuid,user1,LocalDateTime.now().plusHours(24)));
        var cookies = new Cookie[]{new Cookie("sessionId",uuid.toString())};
        var httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        Assertions.assertEquals(true,loginFilter.itsActualSession(httpServletRequest));
    }


}
