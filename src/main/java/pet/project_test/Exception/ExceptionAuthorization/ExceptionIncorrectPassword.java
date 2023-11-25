package pet.project_test.Exception.ExceptionAuthorization;

import jakarta.servlet.ServletException;

public class ExceptionIncorrectPassword extends ServletException {
    public ExceptionIncorrectPassword(String message) {
        super(message);
    }
}
