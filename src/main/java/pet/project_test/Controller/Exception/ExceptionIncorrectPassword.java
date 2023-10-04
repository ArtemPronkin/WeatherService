package pet.project_test.Controller.Exception;

import jakarta.servlet.ServletException;

public class ExceptionIncorrectPassword extends ServletException {
    public ExceptionIncorrectPassword(String message) {
        super(message);
    }
}
