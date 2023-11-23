package pet.project_test.controller.Exception.ExceptionAPI;

import jakarta.servlet.ServletException;

public class ExceptionRequestLimitExceeded extends ServletException {
    public ExceptionRequestLimitExceeded(String message) {
        super(message);
    }
}
