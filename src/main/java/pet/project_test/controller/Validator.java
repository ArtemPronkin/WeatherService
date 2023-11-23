package pet.project_test.controller;

public class Validator {

    public static boolean isValidPassword(String string) {
        return string != null && !string.isBlank();
    }

    public static boolean isValidLogin(String string) {
        return isValidPassword(string);
    }
}
