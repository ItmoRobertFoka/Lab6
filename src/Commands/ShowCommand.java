package Commands;

import Managers.CollectionManager;

public class ShowCommand implements Command {
    private CollectionManager collectionManager;
    private String name;

    public ShowCommand(CollectionManager collectionManager, String name){
        this.collectionManager = collectionManager;
        this.name = name;
    }

    @Override
    public String execute(){
        return collectionManager.show();
    }

    @Override
    public String getDescription(){
        return "show: выводит коллекцию фильмов";
    }

    @Override
    public String getName(){
        return name;
    }
}
