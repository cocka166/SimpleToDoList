import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

public class Task {

    /** Název úkolu */
    private String name;
    /** Datum a čas splnění úkolu */
    private LocalDateTime completionDate;
    /** Počáteční stav úkolu */
    private TaskStatus status = TaskStatus.NotCompleted;
    /** Formátovač pro datum a čas */
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Vytvoří nový úkol.
     * @param name Název úkolu
     * @param completionDate Datum a čas splnění úkolu
     */
    public Task(String name, LocalDateTime completionDate) {
        this.name = name;
        this.completionDate = completionDate;
    }

    public Task(String name, LocalDateTime completionDate, TaskStatus status) {
        this.name = name;
        this.completionDate = completionDate;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;

    }

    /**
     * Převede úkol do formátu CSV.
     * Tento řetězec obsahuje název úkolu, datum a čas splnění úkolu a stav úkolu.
     *
     * @return Řetězec ve formátu CSV, kde jsou hodnoty odděleny čárkami.
     */
    public String toCSV() {
        return name + "," + completionDate.format(DATE_FORMAT) + "," + status.toString();
    }

    /**
     * Vytvoří úkol z řádku ve formátu CSV.
     * Řádek CSV musí obsahovat název úkolu, datum a čas splnění úkolu a stav úkolu.
     *
     * @param csv Řetězec ve formátu CSV, který obsahuje informace o úkolu.
     * @return Novou instanci úkolu vytvořenou z CSV řádku.
     * @throws IllegalArgumentException Pokud zadaný řetězec neobsahuje platný formát nebo obsahuje neplatný stav úkolu.
     */
    public static Task fromCSV(String csv) {
        String[] parts = csv.split(",");
        String name = parts[0];
        LocalDateTime completionDate = LocalDateTime.parse(parts[1], DATE_FORMAT);
        TaskStatus status = TaskStatus.fromText(parts[2]);
        return new Task(name, completionDate, status);
    }

    /** Vrací textovou reprezentaci úkolu */
    @Override
    public String toString() {
        return "úkol: " + name + ", datum splnění: " + completionDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)) + ", stav: " + status;
    }
}
