package pet.project_test.Controller.Servlets;

import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.context.WebContext;
import pet.project_test.Controller.OpenWeatherService.OpenWeatherApiService;
import pet.project_test.Controller.Servlets.Authorization.LoginFilter;
import pet.project_test.Controller.Servlets.ListenerTemplateEngine.TemplateEngineUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.thymeleaf.ITemplateEngine;
import pet.project_test.Entity.Location.LocationDAO;
import pet.project_test.Entity.Session.Session;
import pet.project_test.Entity.Session.SessionDAO;
import pet.project_test.Entity.User.User;
import pet.project_test.Entity.User.UserDAO;

import java.io.IOException;
import java.util.Optional;

@Slf4j

public abstract class BaseServlet extends HttpServlet {
    protected  ITemplateEngine templateEngine;
    protected  WebContext webContext;

    protected OpenWeatherApiService openWeatherApiService = new OpenWeatherApiService();
    protected UserDAO userDAO= new UserDAO();
    protected SessionDAO sessionDAO= new SessionDAO();
    protected LocationDAO locationDAO = new LocationDAO();

    @Override
    public void init(ServletConfig config) throws ServletException {
        templateEngine = (ITemplateEngine) config.getServletContext().getAttribute("templateEngine");
        super.init(config);
        log.info("init template");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        webContext = TemplateEngineUtil.buildWebContext(req, resp, getServletContext());
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var uuid = LoginFilter.getSessionUUIDFromRequest(request).get();
        Optional<Session> optionalSession = sessionDAO.getById(uuid);
        var user = optionalSession.get().getUser();
        get(request, response, user);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var uuid = LoginFilter.getSessionUUIDFromRequest(request).get();
        Optional<Session> optionalSession = sessionDAO.getById(uuid);
        var user = optionalSession.get().getUser();
        post(request, response, user);
    }

    protected void post(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {

    }

    protected void get(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {

    }
}