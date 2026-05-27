package Server.Commands;

import Common.Request;
import Common.Response;
import Server.Managers.CollectionManager;

import java.io.Serializable;

/**
 * Команда, которая удаляет фильм из коллекции по его id.
 * Если в коллекции не было фильма с заданным id, коллекция останется без изменений
 */
public class RemoveByIdCommand implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name = "remove_by_id";
    private CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request){
        Integer removeId;
        try {
            String argument = (String) request.getArgument();
            removeId = Integer.parseInt(argument.trim());
            return new Response(true, collectionManager.remove_by_id(removeId), null);
        } catch (NumberFormatException | NullPointerException e) {
            return new Response(false, "Ошибка, введите корректное число", null);
        }
    }

    @Override
    public String getDescription(){
        return ("remove_by_id: Удаляет фильм по id");
    }

    @Override
    public String getName(){
        return name;
    }
}
