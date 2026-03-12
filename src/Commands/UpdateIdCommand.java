package Commands;

import Managers.CollectionManager;
import java.util.Scanner;
import Input.MovieMaker;
import Model.Movie;

public class UpdateIdCommand implements Command {
    private final String name = "updateId";
    private CollectionManager collectionManager;
    private Scanner scanner;
    private MovieMaker movieMaker;

    public UpdateIdCommand(CollectionManager collectionManager, Scanner scanner) {
        this.collectionManager = collectionManager;
        this.scanner = scanner;
        this.movieMaker = new MovieMaker(scanner);
    }

    @Override
    public String execute() {
        while (true) {
            try {
                System.out.println("Введите id фильма, которого хотите обновить: ");
                int id = Integer.parseInt(scanner.nextLine().trim());
                Movie newMovie = movieMaker.createMovie();
                newMovie.setId(id);
                return collectionManager.updateId(newMovie);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка, id должен быть числом, введите коректное значение: ");
            }
        }
    }

    @Override
    public String getDescription() {
        return "updateId: Обновляет значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String getName() {
        return name;
    }
}
