package pet.project_test.Controller.Servlets.Authorization;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import pet.project_test.Entity.Session.Session;
import pet.project_test.Entity.Session.SessionDAO;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class LoginFilter implements Filter {
    private List<String> excludedUrls;

    SessionDAO sessionDAO = new SessionDAO();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludePattern = filterConfig.getInitParameter("excludedUrls");
        excludedUrls = Arrays.asList(excludePattern.split(","));
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var resp = (HttpServletResponse) servletResponse;
        var req = (HttpServletRequest) servletRequest;
        String path = ((HttpServletRequest) servletRequest).getServletPath();
        log.info("filter path: " + path);

        boolean itsActualSession = itsActualSession(req);

        if (!itsActualSession && !excludedUrls.contains(path)) {
            resp.sendRedirect(((HttpServletRequest) servletRequest).getContextPath() + "/login");
            return;
        }
        if (itsActualSession && excludedUrls.contains(path)) {
            resp.sendRedirect(((HttpServletRequest) servletRequest).getContextPath() + "/home");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }


    public static Optional<UUID> getSessionUUIDFromRequest(HttpServletRequest request) {
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

}
