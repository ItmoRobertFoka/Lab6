package Server.Commands;

import Common.Movie;
import Common.Request;
import Common.Response;
import Server.Managers.CollectionManager;

import java.io.Serializable;

/**
 * Команда, отвечающая за обновление фильма по его id.
 * Если фильма с введенным id не окажется в коллекции, то фильм добавлен не будет
 */
public class UpdateIdCommand implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name = "update_id";
    private final CollectionManager collectionManager;


    public UpdateIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        Object argument = request.getArgument();
        if (argument instanceof Integer) {
            int id = (int) request.getArgument();
            boolean exist = collectionManager.checkId(id);
            if (exist) {
                return new Response(true, "Введите данные нового фильма", null);
            } else {
                return  new Response(false, "Фильм с id " + id + " не найден", null);
            }
        }
        else if (argument instanceof Movie) {
            Movie movie = (Movie) argument;
            int id = movie.getId();
            if (collectionManager.checkId(movie.getId())) {
                collectionManager.updateId(id, movie);
                return new Response(true, "Фильм с id " + id + " заменен", null);
            } else {
                return new Response(false, "" , null);
            }
        }
        return new Response(false, "Введите корректные данные", null);
    }

    @Override
    public String getDescription() {
        return "update_id: обновляет фильм по id";
    }

    @Override
    public String getName() {
        return name;
    }
}