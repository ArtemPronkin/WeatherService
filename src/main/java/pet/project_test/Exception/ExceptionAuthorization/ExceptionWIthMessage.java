package pet.project_test.Exception.ExceptionAuthorization;

import jakarta.servlet.ServletException;

public class ExceptionWIthMessage extends ServletException {
    public ExceptionWIthMessage(String message) {
        super(message);
    }
}
