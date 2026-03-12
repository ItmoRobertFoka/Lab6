package Commands;

import Managers.CollectionManager;
import java.util.Scanner;
import Input.MovieMaker;

public class RemoveGreaterCommand implements Command {
    private final String name = "remove_greater";
    private CollectionManager collectionManager;
    private MovieMaker movieMaker;

    public RemoveGreaterCommand(CollectionManager collectionManager, Scanner scanner) {
        this.collectionManager = collectionManager;
        this.movieMaker = new MovieMaker(scanner);
    }

    @Override
    public String execute() {
        System.out.println("Введите фильм: ");
        return collectionManager.remove_greater(movieMaker.createMovie());
    }

    @Override
    public String getDescription() {
        return "remove_greater: Удаляет из коллекции все элементы, превышающие заданный";
    }

    @Override
    public String getName() {
        return name;
    }
}
