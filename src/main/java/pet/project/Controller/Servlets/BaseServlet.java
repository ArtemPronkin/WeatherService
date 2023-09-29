package pet.project.Controller.Servlets;

import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.context.WebContext;
import pet.project.Controller.TemplateEngineUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.thymeleaf.ITemplateEngine;

import java.io.IOException;
@Slf4j

public abstract class BaseServlet extends HttpServlet {
    protected static ITemplateEngine templateEngine;
    protected static WebContext webContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        templateEngine = (ITemplateEngine) config.getServletContext().getAttribute("templateEngine");
        super.init(config);
        log.info("init template");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        webContext = TemplateEngineUtil.buildWebContext(req,resp,getServletContext());
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

//    protected abstract void Post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
//
//    protected abstract void Get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}