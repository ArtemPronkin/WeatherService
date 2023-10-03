package pet.project_test.Controller.Servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import pet.project_test.Controller.Exception.ExceptionAccess;
import pet.project_test.Controller.Exception.ExceptionEmptyListFound;
import pet.project_test.Controller.Exception.ExceptionLocationAlreadyExist;
import pet.project_test.Controller.Exception.ExceptionWIthMessage;
import pet.project_test.Controller.OpenWeatherService.OpenWeatherApiService;
import pet.project_test.Controller.Servlets.Authorization.LoginFilter;
import pet.project_test.Controller.Servlets.ListenerTemplateEngine.TemplateEngineUtil;
import pet.project_test.Entity.Location.LocationDAO;
import pet.project_test.Entity.Session.Session;
import pet.project_test.Entity.Session.SessionDAO;
import pet.project_test.Entity.User.User;
import pet.project_test.Entity.User.UserDAO;

import java.io.IOException;
import java.util.Optional;

@Slf4j

public abstract class BaseServlet extends HttpServlet {
    protected ITemplateEngine templateEngine;
    protected WebContext webContext;

    protected OpenWeatherApiService openWeatherApiService = new OpenWeatherApiService();
    protected UserDAO userDAO = new UserDAO();
    protected SessionDAO sessionDAO = new SessionDAO();
    protected LocationDAO locationDAO = new LocationDAO();

    @Override
    public void init(ServletConfig config) throws ServletException {
        templateEngine = (ITemplateEngine) config.getServletContext().getAttribute("templateEngine");
        super.init(config);
        log.info("init template");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        webContext = TemplateEngineUtil.buildWebContext(request, response, getServletContext());
        webContext.setVariable("message", request.getParameter("message"));
        try {
            super.service(request, response);
            if (false) {
                throw new ExceptionWIthMessage("");
            }
            if (false) {
                throw new ExceptionAccess("");
            }
            if (false) {
                throw new ExceptionEmptyListFound("");
            }
            if (false) {
                throw new ExceptionLocationAlreadyExist("");
            }
        } catch (ExceptionWIthMessage e) {
            response.sendRedirect(request.getContextPath() + request.getServletPath() + "?message=" + e.getMessageException());
        } catch (ExceptionAccess e) {
            response.sendRedirect(request.getContextPath() + "/login" + "?message=" + e.getMessageException());
        } catch (ExceptionEmptyListFound e) {
            response.sendRedirect(request.getContextPath() + "/home" + "?message=" + e.getMessageException());
        } catch (ExceptionLocationAlreadyExist e) {
            response.sendRedirect(request.getContextPath() + "/home" + "?message=" + e.getMessageException());
        }
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var uuid = LoginFilter.getSessionUUIDFromRequest(request).orElseThrow(() -> new ExceptionAccess("Access closed"));
        Optional<Session> optionalSession = sessionDAO.getById(uuid);
        var user = optionalSession.orElseThrow(() -> new ExceptionAccess("Access closed")).getUser();
        get(request, response, user);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var uuid = LoginFilter.getSessionUUIDFromRequest(request).orElseThrow(() -> new ExceptionAccess("Access closed"));
        Optional<Session> optionalSession = sessionDAO.getById(uuid);
        var user = optionalSession.orElseThrow(() -> new ExceptionAccess("Access closed")).getUser();
        post(request, response, user);
    }

    protected void post(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {

    }

    protected void get(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {

    }
}