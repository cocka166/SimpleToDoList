import java.time.LocalDateTime;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    /** Seznam úkolů */
    private List<Task> tasks = new ArrayList<>();

    /** Přidá nový úkol do seznamu */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /** Vrátí seznam všech úkolů*/
    public List<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Najde úkol podle názvu.
     * @param searchedName Název úkolu k nalezení
     * @return Nalezený úkol nebo null, pokud neexistuje
     */
    public Task findTask(String searchedName) {
        for (Task task : tasks) {
            if (task.getName().equalsIgnoreCase(searchedName)) {
                return task;
            }
        }
        return null;
    }

    /**
     * Smaže úkol podle názvu.
     * @param searchedName Název úkolu k smazání
     * @return True, pokud byl úkol smazán, jinak false
     */
    public boolean removeTask(String searchedName) {
        return tasks.removeIf(task -> task.getName().equalsIgnoreCase(searchedName));
    }

    /**
     * Uloží seznam úkolů do souboru ve formátu CSV.
     * Tato metoda vytvoří soubor s názvem "tasks.csv" (nebo přepíše existující soubor) a uloží každý úkol ze seznamu
     * jako nový řádek ve formátu CSV. Každý úkol je převeden na řetězec pomocí metody toCSV.
     *
     * @throws IOException Pokud dojde k chybě při práci se souborem, např. při vytváření nebo zápisu do souboru.
     */
    public void saveTasksToCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("tasks.csv"))) {
            for (Task task : tasks) {
                writer.println(task.toCSV());
            }
        } catch (IOException e) {
            System.err.println("Došlo k chybě při ukládání úkolů do souboru.");
        }
    }

    /**
     * Načte seznam úkolů ze souboru ve formátu CSV.
     * Tato metoda přečte soubor s názvem "tasks.csv", který obsahuje úkoly v CSV formátu, a vytvoří
     * nové instance úkolů na základě každého řádku v souboru. Pokud soubor neexistuje, seznam úkolů bude prázdný.
     *
     * @throws FileNotFoundException Pokud soubor s úkoly neexistuje.
     * @throws IOException Pokud dojde k chybě při čtení souboru, např. při otevření nebo čtení ze souboru.
     */
    public void loadTasksFromCSV() {
        tasks.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = Task.fromCSV(line);
                tasks.add(task);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Soubor s úkoly nebyl nalezen. Začínáme s prázdným správcem úkolů.");
        } catch (IOException e) {
            System.err.println("Došlo k chybě při načítání úkolů ze souboru.");
        }
    }
}
