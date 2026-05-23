package Server.Commands;

/**
 * Интерфейс, определяющий базовое поведение всех команд.
 */
public interface Command {
    String execute();
    String getDescription();
    String getName();
}
