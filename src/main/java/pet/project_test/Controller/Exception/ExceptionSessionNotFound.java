package pet.project_test.Controller.Exception;

import jakarta.servlet.ServletException;

public class ExceptionSessionNotFound extends ServletException {

    public ExceptionSessionNotFound(String message) {
        super(message);
    }
}
