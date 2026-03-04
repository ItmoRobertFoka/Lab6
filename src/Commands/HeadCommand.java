package Commands;

import Managers.CollectionManager;

public class HeadCommand implements Command {
    private String name = "head";
    private CollectionManager collectionManager;

    public HeadCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(){
        return collectionManager.head();
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
