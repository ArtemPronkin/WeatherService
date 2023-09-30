package pet.project_test.Controller;

public class Validator {

    public static boolean isValidPassword(String string){
        if (string == null || string.isBlank()) {
            return false;
        }
        else
        return true;
    }

    public static boolean isValidLogin (String string){
        return isValidPassword(string);
    }
}
