package pet.project_test.Controller.Servlets.ListenerTemplateEngine;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.TemplateEngine;

@WebListener
public class Listener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        TemplateEngine templateEngine = TemplateEngineUtil.buildTemplateEngine(servletContext);

        servletContext.setAttribute("templateEngine", templateEngine);
    }
}
