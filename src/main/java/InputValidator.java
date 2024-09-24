public class InputValidator {

    /**  Validace vstupu - ověření, zda není vstupní řetězec prázdný nebo null */
    public static boolean isValidString(String input) {
        return input != null && !input.trim().isEmpty();
    }
}