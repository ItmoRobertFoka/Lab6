package server.commands;

import common.Request;
import common.Response;
import server.managers.CollectionManager;

import java.io.Serializable;

/**
 * Команда, которая удаляет фильм из коллекции по его id.
 */
public class RemoveByIdCommand implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name = "remove_by_id";
    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            String argument = (String) request.getArgument();
            int removeId = Integer.parseInt(argument.trim());

            String ownerLogin = request.getLogin();

            String resultMessage = collectionManager.remove_by_id(removeId, ownerLogin);

            return new Response(true, resultMessage, null);
        } catch (NumberFormatException | NullPointerException e) {
            return new Response(false, "Ошибка, введите корректное числовое id", null);
        } catch (Exception e) {
            return new Response(false, "Ошибка выполнения команды: " + e.getMessage(), null);
        }
    }

    @Override
    public String getDescription() {
        return "remove_by_id id: Удаляет фильм по id (только если вы его владелец)";
    }

    @Override
    public String getName() {
        return name;
    }
}