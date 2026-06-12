package server.commands;

import common.Movie;
import common.Request;
import common.Response;
import server.managers.CollectionManager;

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
        try {
            String login = request.getLogin();
            Object argument = request.getArgument();

            if (argument == null) {
                return new Response(false, "Ошибка: Данные запроса пусты.", null);
            }

            if (argument instanceof String) {
                int id = Integer.parseInt((String) argument);

                boolean idExists = collectionManager.checkId(id);
                if (!idExists) {
                    return new Response(false, "Ошибка: Фильма с ID = " + id + " нет в коллекции!", null);
                }

                boolean isOwner = collectionManager.getCollection().stream()
                        .anyMatch(movie -> movie.getId() == id && movie.getOwnerLogin().equals(login));

                if (!isOwner) {
                    return new Response(false, "Ошибка: Вы не являетесь владельцем фильма с ID = " + id + " и не можете его изменить!", null);
                }

                return new Response(true, "ID успешно проверен владельцем. Приступаем к вводу новых данных фильма.", null);
            }

            if (argument instanceof Movie) {
                Movie newMovie = (Movie) argument;
                int id = newMovie.getId();

                boolean success = collectionManager.updateId(id, newMovie, login);

                if (success) {
                    return new Response(true, "Фильм с ID = " + id + " успешно обновлен.", null);
                } else {
                    return new Response(false, "Ошибка при обновлении фильма в базе данных.", null);
                }
            }

            return new Response(false, "Ошибка: Неверный тип данных в запросе.", null);

        } catch (NumberFormatException e) {
            return new Response(false, "Ошибка: ID должен быть числом.", null);
        }
    }

    @Override
    public String getDescription() {
        return "update_id: обновляет фильм по id (только для ваших фильмов)";
    }

    @Override
    public String getName() {
        return name;
    }
}