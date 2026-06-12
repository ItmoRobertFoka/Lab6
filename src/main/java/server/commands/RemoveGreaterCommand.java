package server.commands;

import common.Movie;
import common.Request;
import common.Response;
import server.managers.CollectionManager;

import java.io.Serializable;

/**
 * Команда, которая удаляет все элементы коллекции, в которых поле директора превышает заданное
 */
public class RemoveGreaterCommand implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name = "remove_greater";
    private final CollectionManager collectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        Movie argumentMovie = (Movie) request.getArgument();
        String ownerLogin = request.getLogin();

        String resultMessage = collectionManager.remove_greater(argumentMovie, ownerLogin);

        return new Response(true, resultMessage, null);
    }

    @Override
    public String getDescription() {
        return "remove_greater: Удаляет из коллекции все ваши элементы, превышающие заданный";
    }

    @Override
    public String getName() {
        return name;
    }
}