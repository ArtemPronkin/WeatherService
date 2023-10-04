import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pet.project_test.Controller.Exception.IncorrectPassword;
import pet.project_test.Controller.Exception.UserAlreadyExistsException;
import pet.project_test.Controller.Service.AuthorizationService.SessionUserService;
import pet.project_test.Controller.Service.AuthorizationService.UserAccountService;
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
    static SessionUserService sessionUserService = new SessionUserService();
    static UserAccountService userAccountService = new UserAccountService();
    static User user1 = new User("test@test", "password");
    static HttpServletRequest httpServletRequest;

    @BeforeAll
    static void before() {
        httpServletRequest = mock(HttpServletRequest.class);
        userDAO.save(user1);
    }

    @Test
    public void uniqueUserThrowTest() throws UserAlreadyExistsException {
        userAccountService.registrationNewUser("test222@test", "test@test");
        Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> userAccountService.registrationNewUser("test222@test", "test@test"));
    }

    @Test
    public void InCorrectedPasswordThrowTest() throws UserAlreadyExistsException {
        userAccountService.registrationNewUser("test333@test", "test@test");
        Assertions.assertThrows(IncorrectPassword.class,
                () -> userAccountService.login("test333@test", "NotPassword"));
    }

    @Test
    public void isActualSessionWithEmptyCookieTest() {
        var cookies = new Cookie[0];
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        Assertions.assertEquals(false, sessionUserService.itsActualSession(httpServletRequest));
    }

    @Test
    public void isActualSessionWithRandomUUIDCookieTest() {
        var cookies = new Cookie[]{new Cookie("sessionId", UUID.randomUUID().toString())};
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        Assertions.assertEquals(false, sessionUserService.itsActualSession(httpServletRequest));
    }

    @Test
    public void isActualSessionWithExpiredSessionCookieTest() {
        UUID uuid = UUID.randomUUID();
        sessionDAO.save(new Session(uuid, user1, LocalDateTime.now().minusDays(1)));
        var cookies = new Cookie[]{new Cookie("sessionId", uuid.toString())};
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        Assertions.assertEquals(false, sessionUserService.itsActualSession(httpServletRequest));
    }

    @Test
    public void isActualSessionWithActualSessionCookieTest() {
        var uuid = sessionUserService.createNewSession(user1);
        var cookies = new Cookie[]{new Cookie("sessionId", uuid.toString())};
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        Assertions.assertEquals(true, sessionUserService.itsActualSession(httpServletRequest));
    }

    @Test
    public void deleteExpiredSessionTest() {
        UUID uuid = UUID.randomUUID();
        sessionDAO.save(new Session(uuid, user1, LocalDateTime.now()));
        sessionDAO.deleteExpiredSessions(LocalDateTime.now().plusHours(1));
        Assertions.assertTrue(sessionDAO.getById(uuid).isEmpty());
    }


}
