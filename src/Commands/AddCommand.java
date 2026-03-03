package Commands;

import Managers.CollectionManager;
import Model.Movie;

public class AddCommand implements Command{
    private CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(){
        collectionManager.add(movie);
        return "Фильм добавлен в коллекцию";
    }

    @Override
    public String getDescription(){
        return "add: добавляет новый элемент в коллекцию";
    }

    @Override
    public String getName(){
        return name;
    }
}
