package pet.project_test.controller.Service.AuthorizationService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import pet.project_test.controller.Exception.ExceptionAuthorization.ExceptionAccess;
import pet.project_test.controller.Exception.ExceptionAuthorization.ExceptionWIthMessage;
import pet.project_test.entity.session.Session;
import pet.project_test.entity.session.SessionDAO;
import pet.project_test.entity.user.User;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class SessionUserService {
    SessionDAO sessionDAO = new SessionDAO();

    public UUID createNewSession(User user) {
        UUID uuid = UUID.randomUUID();
        Session session = new Session(uuid, user, LocalDateTime.now().plusHours(24));
        sessionDAO.save(session);
        return uuid;
    }

    public void setSessionCookie(UUID uuid, HttpServletResponse response) {
        Cookie cookie = new Cookie("sessionId", uuid.toString());
        log.info("add Cookie: " + uuid.toString());
        response.addCookie(cookie);
    }

    public void deleteSession(HttpServletRequest request) throws ExceptionWIthMessage, ExceptionAccess {
        var uuid = getSessionUUIDFromRequest(request).orElseThrow(() -> new ExceptionAccess("Session not found"));
        Optional<Session> optionalSession = sessionDAO.getById(uuid);
        sessionDAO.delete(optionalSession.orElseThrow(() -> new ExceptionWIthMessage("Session not found")));
    }

    public boolean itsActualSession(HttpServletRequest request) {
        var optionalIdSessionFromCookie = getSessionUUIDFromRequest(request);
        if (optionalIdSessionFromCookie.isEmpty()) {
            return false;
        }

        Optional<Session> optionalSession = sessionDAO.getById(optionalIdSessionFromCookie.get());
        if (optionalSession.isEmpty()) {
            return false;
        }

        var expiresAt = optionalSession.get().getExpiresAt();
        var currentTime = LocalDateTime.now();
        if (currentTime.isAfter(expiresAt)) {
            return false;
        }
        return true;
    }

    public Optional<UUID> getSessionUUIDFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String cookieName = "sessionId";
        Optional<UUID> uuid = Optional.empty();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (cookieName.equals(c.getName())) {
                    uuid = Optional.of(UUID.fromString(c.getValue()));
                    break;
                }
            }
        }
        return uuid;
    }

    public void setEmptyCookieSession(HttpServletResponse response) {
        Cookie emptyCookie = new Cookie("sessionId", null);
        emptyCookie.setMaxAge(0);
        response.addCookie(emptyCookie);
    }
}
