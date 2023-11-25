package pet.project_test.Exception.ExceptionAuthorization;

import jakarta.servlet.ServletException;

public class ExceptionAccess extends ServletException {
    public ExceptionAccess(String message) {
        super(message);
    }
}
