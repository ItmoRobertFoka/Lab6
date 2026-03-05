package Commands;

import Input.MovieMaker;
import Managers.CollectionManager;
import java.util.Scanner;


public class AddCommand implements Command {
    private String name = "add";
    private CollectionManager collectionManager;
    private Scanner scanner;
    private MovieMaker movieMaker;

    public AddCommand(CollectionManager collectionManager, Scanner scanner){
        this.collectionManager = collectionManager;
        this.scanner = scanner;
        this.movieMaker = new MovieMaker(scanner);
    }

   @Override
   public String execute() {
        collectionManager.add(movieMaker.createMovie());
        return "Фильм успешно создан";
   }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public String getDescription(){
        return "add: Добавляет фильм в коллекцию";
    }
}
