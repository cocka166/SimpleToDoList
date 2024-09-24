import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Application {

    /** Aktuální čas a datum */
    LocalDateTime dateTime = LocalDateTime.now();
    /** Správce úkolů */
    private TaskManager tasks = new TaskManager();
    /** Instance pro vstup od uživatele */
    private UserInput userInput = new UserInput();

    /**
     * Spustí hlavní smyčku aplikace.
     * Program běží, dokud uživatel nezvolí ukončení.
     */
    public void run() {
        tasks.loadTasksFromCSV();
        boolean running = true;
        while (running) {
            System.out.println("VÍTEJTE V APLIKACI TO DO APP!");
            System.out.println("Dnes je " + dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)) + ".");
            displayToDoTasks();
            printMenu();
            int choice = userInput.getIntInput("Vyberte možnost: ");

            switch (choice) {
                case 1:
                    /** Vytvoří nový úkol */
                    createNewTask();
                    break;
                case 2:
                    /** Zobrazí všechny úkoly*/
                    displayAllTasks();
                    break;
                case 3:
                    /** Nastaví úkol jako splněný */
                    setTaskAsCompleted();
                    break;
                case 4:
                    /** Smaže úkol */
                    removeTask();
                    break;
                case 5:
                    /** Ukončí aplikaci */
                    running = false;
                    break;
                default:
                    System.out.println("Neplatná volba, zkuste to znovu.");
            }
        }
        System.out.println("Na shledanou!");
    }

    /** Zobrazí možnosti pro uživatele */
    public void printMenu() {
        System.out.println("1. Vytvoření nového úkolu");
        System.out.println("2. Zobrazení seznamu všech úkolů");
        System.out.println("3. Označit úkol jako splněný");
        System.out.println("4. Smazat úkol");
        System.out.println("5. Konec");
    }

    /** Vytvoří nový úkol na základě vstupu od uživatele */
    public void createNewTask() {
        String name = userInput.getStringInput("Zadejte název úkolu: ");
        LocalDateTime completionDate = userInput.getDateTimeInput("Zadejte datum a čas pro splnění úkolu formátu yyyy-MM-dd HH:mm:");

        Task task = new Task(name, completionDate);
        tasks.addTask(task);
        tasks.saveTasksToCSV();
        System.out.println("Úkol byl úspěšně vytvořen.");
    }

    /** Zobrazí všechny úkoly */
    public void displayAllTasks() {
        System.out.println("SEZNAM VŠECH ÚKOLŮ");
        for (Task task : tasks.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println();
    }

    /** Označí úkol jako splněný, pokud je nalezen */
    public void setTaskAsCompleted() {
        String searchedName = userInput.getStringInput("Zadejte název úkolu: ");
        Task task = tasks.findTask(searchedName);

        if (task != null) { // Pokud je úkol nalezen
            task.setStatus(TaskStatus.Completed);
            tasks.saveTasksToCSV();
            System.out.println("Úkol splněn!");
        } else {
            System.out.println("Úkol nenalezen.");
        }
        System.out.println();
    }

    /** Smaže úkol podle názvu, pokud existuje */
    public void removeTask() {
        String searchedName = userInput.getStringInput("Zadejte název úkolu: ");
        if (tasks.removeTask(searchedName)) {
            tasks.saveTasksToCSV();
            System.out.println("Úkol smazán!");
        } else {
            System.out.println("Úkol nenalezen.");
        }
        System.out.println();
    }

    /** Zobrazí úkoly naplánované na dnešek a zítřek */
    public void displayToDoTasks() {
        LocalDate today = dateTime.toLocalDate();
        LocalDate tomorrow = today.plusDays(1);
        for (Task task : tasks.getAllTasks()) {
            LocalDate toDoday = task.getCompletionDate().toLocalDate();
            if (toDoday.isEqual(today)) {
                System.out.println("Úkoly na dnes: ");
                System.out.println(task);
            } else if (toDoday.isEqual(tomorrow)) {
                System.out.println("Úkoly na zítra: ");
                System.out.println(task);
            }
        }
    }
}




