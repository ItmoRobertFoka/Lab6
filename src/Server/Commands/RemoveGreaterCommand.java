package Server.Commands;

import Common.Movie;
import Common.Request;
import Common.Response;
import Server.Managers.CollectionManager;

import java.io.Serializable;

/**
 * Команда, которая удаляет все элементы коллекции, в которых поле директора превышает заданное
 */
public class RemoveGreaterCommand implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name = "remove_greater";
    private CollectionManager collectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return new Response(true, collectionManager.remove_greater((Movie) request.getArgument()), null);
    }

    @Override
    public String getDescription() {
        return "remove_greater: Удаляет из коллекции все элементы, превышающие заданный";
    }

    @Override
    public String getName() {
        return name;
    }
}
