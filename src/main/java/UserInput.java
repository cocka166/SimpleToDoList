import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserInput {

    /** Čte vstupy od uživatele */
    private Scanner scanner = new Scanner(System.in);

    /**
     * Získá vstup od uživatele jako řetězec.
     * Opakuje se, dokud není vstup validní.
     */
    public String getStringInput(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine();
        }
        while (!isValidString(input));
        return input;
    }

    /**
     * Získá vstup od uživatele jako celé číslo.
     * Opakuje se, dokud není vstup validní.
     */
    public int getIntInput(String prompt) {
        int input;
        while (true) {
            try {
                System.out.print(prompt);
                input = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Neplatný vstup. Zadejte číslo.");
            }
        }
        return input;
    }

    /**
     * Získá vstup od uživatele jako datum a čas.
     * Opakuje se, dokud není vstup validní.
     */
    public LocalDateTime getDateTimeInput(String prompt){
        LocalDateTime input;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        while (true) {
            try {
                System.out.print(prompt);
                input = LocalDateTime.parse(scanner.nextLine(), formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Neplatný vstup. Zadejte datum a čas ve formátu yyyy-MM-dd HH:mm.");
            }
        }
        return input;
    }

    /**  Validace vstupu - ověření, zda není vstupní řetězec prázdný nebo null */
    public boolean isValidString(String input) {
        return input != null && !input.trim().isEmpty();
    }

}
