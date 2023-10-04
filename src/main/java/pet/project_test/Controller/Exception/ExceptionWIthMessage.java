package pet.project_test.Controller.Exception;

import jakarta.servlet.ServletException;

public class ExceptionWIthMessage extends ServletException {
    public ExceptionWIthMessage(String message) {
        super(message);
    }
}
