package pet.project_test.Exception.ExceptionAuthorization;

import jakarta.servlet.ServletException;

public class ExceptionSessionNotFound extends ServletException {

    public ExceptionSessionNotFound(String message) {
        super(message);
    }
}
