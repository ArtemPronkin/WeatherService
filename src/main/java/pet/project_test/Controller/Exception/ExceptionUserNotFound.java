package pet.project_test.Controller.Exception;

import jakarta.servlet.ServletException;

public class ExceptionUserNotFound extends ServletException {
    public ExceptionUserNotFound(String message) {
        super(message);
    }
}
