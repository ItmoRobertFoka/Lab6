package Commands;

import Managers.CollectionManager;

public class HeadCommand implements Command {
    private final String name = "head";
    private CollectionManager collectionManager;

    public HeadCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(){
        return "Первый элемент коллекции: " + collectionManager.head();
    }

    @Override
    public String getDescription(){
        return "head: Выводит первый элемент коллекции";
    }

    @Override
    public String getName(){
        return name;
    }
}
