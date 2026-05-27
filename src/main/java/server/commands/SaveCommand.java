package server.commands;

import common.Request;
import common.Response;
import server.managers.CollectionManager;

import java.io.Serializable;

/**
 * Команда, которая сохраняет коллекцию в файл.
 */
public class SaveCommand implements Command, Serializable {
    private final static long serialVersionUID = 1L;
    private final String name = "save";
    private CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return new Response(true, collectionManager.save(), null);
    }

    @Override
    public String getDescription() {
        return "save: Сохраняет коллекцию в файл";
    }

    @Override
    public String getName() {
        return name;
    }
}
