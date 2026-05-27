package server.commands;

import common.Request;
import common.Response;
import server.managers.CollectionManager;

import java.io.Serializable;

/**
 * Команда, которая выводит элементы, значение поля name которых содержит заданную подстроку
 */
public class FilterContainsNameCommand implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name = "filter_contains_name";
    private CollectionManager collectionManager;

    public FilterContainsNameCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        String argument =  (String) request.getArgument();
        String name = argument.trim();
        String result = collectionManager.filter_contains_name(name);

        if (result.isEmpty()) {
            return new Response(false,"Совпадения не найдены", null);
        }
        return new Response(true, result, null);
    }

    @Override
    public String getDescription() {
        return "filter_contains_name: выводит элементы, значение поля name которых содержит заданную подстроку";
    }

    @Override
    public String getName() {
        return name;
    }
}
