package Server.Commands;

import Server.CollectionManager;
import Client.MovieMaker;
import Client.InputManager;

/**
 * Команда, которая выводит колличество элементов коллекции,
 * в которых значиение поле director больше чем у заданного.
 */
public class CountGreaterThanDirectorCommand implements Command {
    private final String name = "count_greater_than_director";
    private CollectionManager collectionManager;
    private MovieMaker movieMaker;

    public CountGreaterThanDirectorCommand(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.movieMaker = new MovieMaker(inputManager);
    }

    @Override
    public String execute() {
        return "Колличество фильмов: " + collectionManager.count_greater_than_director(movieMaker.inputDirector());
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
