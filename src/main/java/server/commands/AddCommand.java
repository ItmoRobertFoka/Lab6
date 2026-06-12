package server.commands;

import common.Movie;
import common.Request;
import common.Response;
import server.managers.CollectionManager;

import java.io.Serializable;

/**
 * Команда, которая добавляет фильм в коллекцию.
 */
public class AddCommand implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name = "add";
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            Movie movie = (Movie) request.getArgument();
            String ownerLogin = request.getLogin();
            collectionManager.add(movie, ownerLogin);

            return new Response(true, "Фильм успешно добавлен в коллекцию", null);
        } catch (Exception e) {
            return new Response(false, "Ошибка при добавлении: " + e.getMessage(), null);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "add: Добавляет фильм в коллекцию с привязкой к вашему аккаунту";
    }
}