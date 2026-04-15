package Commands;

import Managers.CollectionManager;
import Managers.InputManager;
import Input.MovieMaker;
import Model.Movie;

/**
 * Команда, отвечающая за обновление фильма по его id.
 * Если фильма с введенным id не окажется в коллекции, то фильм добавлен не будет
 */
public class UpdateIdCommand implements Command {

    private final String name = "update_id";
    private final CollectionManager collectionManager;
    private final InputManager inputManager;
    private final MovieMaker movieMaker;

    public UpdateIdCommand(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
        this.movieMaker = new MovieMaker(inputManager);
    }

    @Override
    public String execute() {

        try {
            System.out.println("Введите id фильма:");

            int id = Integer.parseInt(inputManager.nextLine().trim());

            boolean updated = collectionManager.updateId(id);

            if (updated) {
                return "Фильм с id " + id + " обновлён";
            } else {
                return "Фильм с id " + id + " не найден";
            }

        } catch (NumberFormatException e) {
            return "Ошибка: id должен быть числом";
        }
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