package Commands;

import Input.MovieMaker;
import Managers.CollectionManager;
import Managers.InputManager;

/**
 * Команда, которая добавляет фильм в коллекцию.
 * Использует динамический ввод.
 */
public class AddCommand implements Command {
    private final String name = "add";
    private CollectionManager collectionManager;
    private MovieMaker movieMaker;

    public AddCommand(CollectionManager collectionManager, InputManager inputManager){
        this.collectionManager = collectionManager;
        this.movieMaker = new MovieMaker(inputManager);
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
