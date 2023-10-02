
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
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SessionTest {
    static UserDAO userDAO = new UserDAO();
    static SessionDAO sessionDAO = new SessionDAO();
    static LoginFilter loginFilter = new LoginFilter();
    static User user1 = new User("test@test", "password");
    static HttpServletRequest httpServletRequest ;

    @BeforeAll
    static void before(){
        httpServletRequest = mock(HttpServletRequest.class);
        userDAO.save(user1);
    }

    @Test
    public void uniqueUserThrowTest() {
        var user2 = new User("test@test", "22password22");
        Assertions.assertThrows(PersistenceException.class, () -> userDAO.save(user2));
    }
    @Test
    public void isActualSessionWithEmptyCookieTest(){
        var cookies = new Cookie[0];
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        Assertions.assertEquals(false,loginFilter.itsActualSession(httpServletRequest));
    }

    @Test
    public void isActualSessionWithRandomUUIDCookieTest(){
        var cookies = new Cookie[]{new Cookie("sessionId",UUID.randomUUID().toString())};
        when(httpServletRequest.getCookies()).thenReturn(cookies);

        Assertions.assertEquals(false,loginFilter.itsActualSession(httpServletRequest));
    }
    @Test
    public void isActualSessionWithExpiredSessionCookieTest(){
        UUID uuid = UUID.randomUUID();
        sessionDAO.save(new Session(uuid,user1,LocalDateTime.now().minusDays(1)));
        var cookies = new Cookie[]{new Cookie("sessionId",uuid.toString())};
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        Assertions.assertEquals(false,loginFilter.itsActualSession(httpServletRequest));
    }
    @Test
    public void isActualSessionWithActualSessionCookieTest(){
        UUID uuid = UUID.randomUUID();
        sessionDAO.save(new Session(uuid,user1,LocalDateTime.now().plusHours(24)));
        var cookies = new Cookie[]{new Cookie("sessionId",uuid.toString())};
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        Assertions.assertEquals(true,loginFilter.itsActualSession(httpServletRequest));
    }


}
