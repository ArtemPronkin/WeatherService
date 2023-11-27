import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pet.project_test.Exception.ExceptionAuthorization.ExceptionIncorrectPassword;
import pet.project_test.Exception.ExceptionAuthorization.ExceptionUserAlreadyExistsException;
import pet.project_test.Exception.ExceptionAuthorization.ExceptionUserNotFound;
import pet.project_test.Service.AuthorizationService.SessionUserService;
import pet.project_test.Service.AuthorizationService.UserAccountService;
import pet.project_test.entity.location.Location;
import pet.project_test.entity.location.LocationDAO;
import pet.project_test.entity.session.Session;
import pet.project_test.entity.session.SessionDAO;
import pet.project_test.entity.user.User;
import pet.project_test.entity.user.UserDAO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SessionTest {
    static UserDAO userDAO = new UserDAO();
    static SessionDAO sessionDAO = new SessionDAO();
    static SessionUserService sessionUserService = new SessionUserService();
    static UserAccountService userAccountService = new UserAccountService();
    static LocationDAO locationDAO = new LocationDAO();
    static User user1 = new User("test@test", "password");
    static HttpServletRequest httpServletRequest;

    @BeforeAll
    static void before() {
        httpServletRequest = mock(HttpServletRequest.class);
        userDAO.save(user1);
    }

    @Test
    void Must_ThrowException_WhenRegisteringAnExistingEmail() throws ExceptionUserAlreadyExistsException {
        userAccountService.registrationNewUser("test222@test", "test@test");
        Assertions.assertThrows(ExceptionUserAlreadyExistsException.class,
                () -> userAccountService.registrationNewUser("test222@test", "test@test"));
    }

    @Test
    void Must_ThrowException_WhenLoginWithIncorrectPassword() throws ExceptionUserAlreadyExistsException {
        userAccountService.registrationNewUser("test333@test", "test@test");
        Assertions.assertThrows(ExceptionIncorrectPassword.class,
                () -> userAccountService.login("test333@test", "NotPassword"));
    }

    @Test
    void Must_IsNotActualSession_WhenCookiesIsEmpty() {
        var cookies = new Cookie[0];
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        Assertions.assertFalse(sessionUserService.itsActualSession(httpServletRequest));
    }

    @Test
    void Must_IsNotActualSession_WhenCookiesContainsRandomUUID() {
        var cookies = new Cookie[]{new Cookie("sessionId", UUID.randomUUID().toString())};
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        Assertions.assertFalse(sessionUserService.itsActualSession(httpServletRequest));
    }

    @Test
    void Must_IsNotActualSession_WhenCookiesContainsUUIDExpiredSession() {
        UUID uuid = UUID.randomUUID();
        sessionDAO.save(new Session(uuid, user1, LocalDateTime.now().minusDays(1)));
        var cookies = new Cookie[]{new Cookie("sessionId", uuid.toString())};
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        Assertions.assertFalse(sessionUserService.itsActualSession(httpServletRequest));
    }

    @Test
    void Must_IsActualSession_WhenCookiesContainsCorrectUUID() {
        var uuid = sessionUserService.createNewSession(user1);
        var cookies = new Cookie[]{new Cookie("sessionId", uuid.toString())};
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        Assertions.assertTrue(sessionUserService.itsActualSession(httpServletRequest));
    }

    @Test
    void Must_DeleteSession_WhenSessionExpired() {
        UUID uuid = UUID.randomUUID();
        sessionDAO.save(new Session(uuid, user1, LocalDateTime.now()));
        sessionDAO.deleteExpiredSessions(LocalDateTime.now().plusHours(1));
        Assertions.assertTrue(sessionDAO.getById(uuid).isEmpty());
    }

    @Test
    void Must_EmptyLocationList_WhenUserDeleteLocation() throws ExceptionUserAlreadyExistsException, ExceptionIncorrectPassword, ExceptionUserNotFound {
        var login = "user";
        var password = login;
        var user6 = userAccountService.registrationNewUser(login, password);
        var lat = new BigDecimal(10);
        var lot = new BigDecimal(20);
        var loc = new Location(user1, lat, lot);
        locationDAO.save(loc);
        locationDAO.deleteByUser(user1, lat, lot);
        user6 = userAccountService.login(login, password);
        Assertions.assertTrue(user6.getLocationList().isEmpty());
    }
}
