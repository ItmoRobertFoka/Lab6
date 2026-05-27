package Server.Commands;

import Common.Person;
import Common.Request;
import Common.Response;
import Server.Managers.CollectionManager;


/**
 * Команда, которая выводит колличество элементов коллекции,
 * в которых значиение поле director больше чем у заданного.
 */
public class CountGreaterThanDirectorCommand implements Command {
    private final String name = "count_greater_than_director";
    private CollectionManager collectionManager;

    public CountGreaterThanDirectorCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            Person newDirector = (Person) request.getArgument();
            return new Response(true,"Колличество фильмов: " + collectionManager.count_greater_than_director(newDirector),
                    null);
        } catch (ClassCastException e) {
            return new Response(false, "Введите корректные данные директора", null);
        }

    }

    @Override
    public String getDescription() {
        return "count_greater_than_director: Выводит количество элементов, значение поля director у которых больше заданного";
    }

    @Override
    public String getName() {
        return name;
    }
}
