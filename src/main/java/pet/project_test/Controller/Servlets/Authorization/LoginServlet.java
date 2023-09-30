package pet.project_test.Controller.Servlets.Authorization;

import com.password4j.Password;
import lombok.extern.slf4j.Slf4j;
import pet.project_test.Controller.Servlets.BaseServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pet.project_test.Controller.Validator;
import pet.project_test.Entity.Session.Session;
import pet.project_test.Entity.Session.SessionDAO;
import pet.project_test.Entity.User.UserDAO;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        webContext.setVariable("message",request.getParameter("message"));
        templateEngine.process("login",webContext,response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (!Validator.isValidLogin(login) || !Validator.isValidPassword(password)){
            response.sendRedirect(request.getContextPath()+"/login?message="+"Not valid" );
            return;
        }

        var user = UserDAO.findByLogin(login);
        if (user.isEmpty()){
            response.sendRedirect(request.getContextPath()+"/login?message="+"User not found" );
            return;
        }
        var check = Password.check(password,user.get().getPassword()).withBcrypt();
        if ( ! check){
            response.sendRedirect(request.getContextPath()+"/login?message="+"Incorrect password" );
            return;
        }

        Session session = new Session(UUID.randomUUID(), user.get(), LocalDateTime.now().plusHours(24));
        SessionDAO.save(session);
        Cookie cookie = new Cookie("sessionId", session.getId().toString());
        log.info("add Cookie: " + session.getId().toString());
        response.addCookie(cookie);

        response.sendRedirect(request.getContextPath()+"/home");
    }
}