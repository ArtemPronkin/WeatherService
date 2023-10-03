package pet.project_test.Controller.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExceptionLocationAlreadyExist extends Exception {
    String messageException;
}
