package Server.Commands;

import Common.Request;
import Common.Response;
import Server.Managers.CollectionManager;

import java.io.Serializable;

/**
 * Команда, которая выводит информацию о коллекции
 */
public class InfoCommand implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name = "info";
    private CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return new Response(true, collectionManager.info(), null);
    }

    @Override
    public String getDescription(){
        return "info: Выводит в стандартный поток информацию о коллекции";
    }

    @Override
    public String getName(){
        return name;
    }
}
