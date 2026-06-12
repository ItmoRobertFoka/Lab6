package server.commands;

import common.Movie;
import common.Request;
import common.Response;
import server.managers.CollectionManager;

import java.io.Serializable;

/**
 * Команда, которая добавляет фильм в коллекцию,
 * если его значение меньше минимального.
 */
public class AddIfMinCommand implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name = "add_if_min";
    private final CollectionManager collectionManager;

    public AddIfMinCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        Movie movie = (Movie) request.getArgument();
        String ownerLogin = request.getLogin();

        if (collectionManager.add_if_min(movie, ownerLogin)) {
            return new Response(true, "Фильм успешно добавлен в коллекцию", null);
        }
        return new Response(false, "Фильм не добавлен, так как он не меньше минимального элемента коллекции", null);
    }

    @Override
    public String getDescription() {
        return "add_if_min: Добавляет фильм в коллекцию, если его значение меньше, чем у минимального элемента";
    }

    @Override
    public String getName() {
        return name;
    }
}