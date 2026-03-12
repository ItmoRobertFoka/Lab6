package Commands;

import Input.MovieMaker;
import Managers.CollectionManager;

import java.util.Scanner;

public class AddIfMinCommand implements Command {
    private final String name = "add_if_min";
    private CollectionManager collectionManager;
    private MovieMaker movieMaker;

    public AddIfMinCommand(CollectionManager collectionManager, Scanner scanner) {
        this.collectionManager = collectionManager;
        this.movieMaker = new MovieMaker(scanner);
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
