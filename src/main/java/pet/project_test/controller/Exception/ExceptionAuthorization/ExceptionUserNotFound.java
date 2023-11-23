package pet.project_test.controller.Exception.ExceptionAuthorization;

import jakarta.servlet.ServletException;

public class ExceptionUserNotFound extends ServletException {
    public ExceptionUserNotFound(String message) {
        super(message);
    }
}
