package pet.project_test.Controller.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionEmptyListFound extends Exception {
    String messageException;
}
