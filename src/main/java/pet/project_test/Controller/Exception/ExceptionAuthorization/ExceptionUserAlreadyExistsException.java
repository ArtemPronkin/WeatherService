package pet.project_test.Controller.Exception.ExceptionAuthorization;

import jakarta.servlet.ServletException;

public class ExceptionUserAlreadyExistsException extends ServletException {
    public ExceptionUserAlreadyExistsException(String message) {
        super(message);
    }
}