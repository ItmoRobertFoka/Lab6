package server.commands;

import common.Request;
import common.Response;
import server.managers.CollectionManager;

import java.io.Serializable;

/**
 * Команда, удаляющая все элементы из коллекции.
 */
public class ClearCommand implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name = "clear";
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request){
        String ownerLogin = request.getLogin();

        String resultMessage = collectionManager.clear(ownerLogin);

        return new Response(true, resultMessage, null);
    }

    @Override
    public String getDescription(){
        return "clear: Очищает коллекцию (удаляет только ваши элементы)";
    }

    @Override
    public String getName(){
        return name;
    }
}