package server.commands;

import common.Movie;
import common.Request;
import common.Response;
import server.managers.CollectionManager;

import java.io.Serializable;

/**
 * Команда, которая добавляет фильм в коллецию,
 * если его значение меньше минимального.
 */
public class AddIfMinCommand implements Command, Serializable {
    private static final long serialVesrionUID = 1L;
    private final String name = "add_if_min";
    private final CollectionManager collectionManager;

    public AddIfMinCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        if (collectionManager.add_if_min((Movie) request.getArgument())) {
            return new Response(true, "Фильм добавлен в коллекцию",null);
        }
        return new Response(false, "Фильм не добавлен в коллекцию тк не является минимальным", null);
    }

    @Override
    public String getDescription() {
        return "add_if_min: Добавляет фильм в коллекцию если его значение меньше чем у минимального в коллекции";
    }

    @Override
    public String getName() {
        return name;
    }
}
