package pet.project_test.controller.Servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import pet.project_test.Exception.ExceptionAPI.ExceptionEmptyListFound;
import pet.project_test.Exception.ExceptionAPI.ExceptionLocationAlreadyExist;
import pet.project_test.Exception.ExceptionAPI.ExceptionOpenWeatherError;
import pet.project_test.Exception.ExceptionAPI.ExceptionRequestLimitExceeded;
import pet.project_test.Exception.ExceptionAuthorization.*;
import pet.project_test.Service.AuthorizationService.SessionUserService;
import pet.project_test.Service.AuthorizationService.UserAccountService;
import pet.project_test.Service.OpenWeatherService.OpenWeatherApiService;
import pet.project_test.controller.Servlets.ListenerTemplateEngine.TemplateEngineUtil;
import pet.project_test.entity.user.User;

import java.io.IOException;

@Slf4j

public abstract class BaseServlet extends HttpServlet {
    protected ITemplateEngine templateEngine;
    protected WebContext webContext;

    protected OpenWeatherApiService openWeatherApiService = new OpenWeatherApiService();
    protected UserAccountService userAccountService = new UserAccountService();
    protected SessionUserService sessionUserService = new SessionUserService();

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
        } catch (ExceptionWIthMessage | ExceptionUserAlreadyExistsException | ExceptionUserNotFound |
                 ExceptionSessionNotFound | ExceptionRequestLimitExceeded | ExceptionOpenWeatherError |
                 ExceptionIncorrectPassword e) {
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

        get(request, response, sessionUserService.getUser(request));
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        post(request, response, sessionUserService.getUser(request));
    }

    protected void post(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {

    }

    protected void get(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {

    }
}