package Server.Commands;

import Common.Request;
import Common.Response;
import Server.Managers.CollectionManager;

import java.io.Serializable;

/**
 * Команда, удаляющая все элементы из коллекции.
 */
public class ClearCommand implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name = "clear";
    private CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request){
        collectionManager.clear();
        return new Response(true, "Коллекция очищена", null);
    }

    @Override
    public String getDescription(){
        return "clear: Очищает коллекцию";
    }

    @Override
    public String getName(){
        return name;
    }
}
