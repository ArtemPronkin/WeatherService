package pet.project_test.Exception.ExceptionAuthorization;

import jakarta.servlet.ServletException;

public class ExceptionUserNotFound extends ServletException {
    public ExceptionUserNotFound(String message) {
        super(message);
    }
}
