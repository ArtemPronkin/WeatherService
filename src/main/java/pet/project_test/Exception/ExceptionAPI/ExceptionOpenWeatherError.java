package pet.project_test.Exception.ExceptionAPI;

import jakarta.servlet.ServletException;

public class ExceptionOpenWeatherError extends ServletException {
    public ExceptionOpenWeatherError(String message) {
        super(message);
    }
}
