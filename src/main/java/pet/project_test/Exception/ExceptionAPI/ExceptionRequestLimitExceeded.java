package pet.project_test.Exception.ExceptionAPI;

import jakarta.servlet.ServletException;

public class ExceptionRequestLimitExceeded extends ServletException {
    public ExceptionRequestLimitExceeded(String message) {
        super(message);
    }
}
