package Commands;

import Input.MovieMaker;
import Managers.CollectionManager;
import Managers.InputManager;

import java.util.Scanner;

/**
 * Команда, которая добавляет фильм в коллецию,
 * если его значение меньше минимального.
 */
public class AddIfMinCommand implements Command {
    private final String name = "add_if_min";
    private CollectionManager collectionManager;
    private MovieMaker movieMaker;

    public AddIfMinCommand(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.movieMaker = new MovieMaker(inputManager);
    }

    @Override
    public String execute() {
        System.out.println("Введите фильм, который хотите добавить: ");
        return collectionManager.add_if_min(movieMaker.createMovie());
    }

    @Override
    public String getDescription() {
        return "add_if_min: Добавляет фильм в коллекцию если его значение меньше чем у минимального в коллекции";
    }

    @Override
    public String getName() {
        return name;
    }
}
