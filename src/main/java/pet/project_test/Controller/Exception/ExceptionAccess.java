package pet.project_test.Controller.Exception;

import jakarta.servlet.ServletException;

public class ExceptionAccess extends ServletException {
    public ExceptionAccess(String message) {
        super(message);
    }
}
