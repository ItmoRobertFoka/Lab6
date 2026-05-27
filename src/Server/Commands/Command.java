package Server.Commands;

import Common.Request;
import Common.Response;

/**
 * Интерфейс, определяющий базовое поведение всех команд.
 */
public interface Command {
    Response execute(Request request);
    String getDescription();
    String getName();
}
