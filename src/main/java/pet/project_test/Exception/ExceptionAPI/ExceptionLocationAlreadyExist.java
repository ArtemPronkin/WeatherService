package pet.project_test.Exception.ExceptionAPI;

import jakarta.servlet.ServletException;

public class ExceptionLocationAlreadyExist extends ServletException {
    public ExceptionLocationAlreadyExist(String message) {
        super(message);
    }
}
