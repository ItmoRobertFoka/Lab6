package server.commands;

import common.Request;
import common.Response;
import server.managers.CollectionManager;

import java.io.Serializable;

/**
 * Команда, которая суммирует все значения поля golden_palm_count
 * у всех фильмов в коллекции.
 */
public class SumOfGoldenPalmCountCommand implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name = "sum_of_golden_palm_count";
    private CollectionManager collectionManager;

    public SumOfGoldenPalmCountCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return new Response(true, "Сумма Золотых пальм: "  + collectionManager.sum_of_golden_palm_count(), null);
    }

    @Override
    public String getDescription(){
        return "sum_of_golden_palm_count: Выводит сумму \"Золотых пальм\" всех элеметов коллекции";
    }

    @Override
    public String getName(){
        return name;
    }
}
