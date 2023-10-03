package pet.project_test.Controller.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExceptionAccess extends Throwable {
    private String messageException;
}
