package pet.project_test.Controller.Exception;

import jakarta.servlet.ServletException;

public class ExceptionEmptyListFound extends ServletException {
    public ExceptionEmptyListFound(String message) {
        super(message);
    }
}
