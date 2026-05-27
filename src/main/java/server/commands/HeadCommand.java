package server.commands;

import common.Request;
import common.Response;
import server.managers.CollectionManager;

import java.io.Serializable;

/**
 * Команда, которая выводит первый элемент коллекции.
 * Если в коллекции нет элементов, то выведет, что коллекция пуста.
 */
public class HeadCommand implements Command, Serializable {
    public static final long serialVersionUID = 1L;
    private final String name = "head";
    private CollectionManager collectionManager;

    public HeadCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request){
        return new Response(true, "Первый элемент коллекции: " + collectionManager.head(), null);
    }

    @Override
    public String getDescription(){
        return "head: Выводит первый элемент коллекции";
    }

    @Override
    public String getName(){
        return name;
    }
}
