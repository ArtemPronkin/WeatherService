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
import pet.project_test.Controller.Exception.*;
import pet.project_test.Controller.Service.AuthorizationService.SessionUserService;
import pet.project_test.Controller.Service.AuthorizationService.UserAccountService;
import pet.project_test.Controller.Service.OpenWeatherService.OpenWeatherApiService;
import pet.project_test.Controller.Servlets.ListenerTemplateEngine.TemplateEngineUtil;
import pet.project_test.Entity.Location.LocationDAO;
import pet.project_test.Entity.Session.SessionDAO;
import pet.project_test.Entity.User.User;
import pet.project_test.Entity.User.UserDAO;

import java.io.IOException;

@Slf4j

public abstract class BaseServlet extends HttpServlet {
    protected ITemplateEngine templateEngine;
    protected WebContext webContext;

    protected OpenWeatherApiService openWeatherApiService = new OpenWeatherApiService();
    protected UserAccountService userAccountService = new UserAccountService();
    protected SessionUserService sessionUserService = new SessionUserService();
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
            forCatchExceptions();
        } catch (ExceptionWIthMessage | UserAlreadyExistsException | UserNotFound | SessionNotFound |
                 IncorrectPassword e) {
            response.sendRedirect(request.getContextPath() + request.getServletPath() + "?message=" + e.getMessage());
        } catch (ExceptionAccess e) {
            response.sendRedirect(request.getContextPath() + "/login" + "?message=" + e.getMessage());
        } catch (ExceptionEmptyListFound | ExceptionLocationAlreadyExist e) {
            response.sendRedirect(request.getContextPath() + "/home" + "?message=" + e.getMessage());
        }
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        get(request, response, userAccountService.getUser(request));
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        post(request, response, userAccountService.getUser(request));
    }

    protected void post(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {

    }

    protected void get(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {

    }

    private void forCatchExceptions() throws ExceptionWIthMessage, ExceptionAccess, ExceptionEmptyListFound, ExceptionLocationAlreadyExist, UserAlreadyExistsException, IncorrectPassword, UserNotFound, SessionNotFound {
        if (false) {
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
            if (false) {
                throw new UserAlreadyExistsException("");
            }
            if (false) {
                throw new IncorrectPassword("");
            }
            if (false) {
                throw new SessionNotFound("");
            }
            if (false) {
                throw new UserNotFound("");
            }
        }
    }
}