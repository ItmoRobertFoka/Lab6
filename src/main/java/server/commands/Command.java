package server.commands;

import common.Request;
import common.Response;

/**
 * Интерфейс, определяющий базовое поведение всех команд.
 */
public interface Command {
    Response execute(Request request);
    String getDescription();
    String getName();
}
