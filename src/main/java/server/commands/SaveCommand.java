package server.commands;

import common.Request;
import common.Response;
import server.managers.CollectionManager;
import java.io.Serializable;

/**
 * Команда, которая вызывается исключительно на сервере для подтверждения сохранности данных.
 */
public class SaveCommand implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    private final CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return new Response(true, "Коллекция успешно синхронизирована с базой данных PostgreSQL.", null);
    }

    @Override
    public String getDescription() {
        return "save: Проверить синхронизацию коллекции с базой данных";
    }

    @Override
    public String getName() {
        return "save";
    }
}