package pet.project_test.Exception.ExceptionAPI;

import jakarta.servlet.ServletException;

public class ExceptionEmptyListFound extends ServletException {
    public ExceptionEmptyListFound(String message) {
        super(message);
    }
}
