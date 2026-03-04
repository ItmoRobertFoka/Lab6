package Commands;

import Managers.CollectionManager;

public class ShowCommand implements Command {
    private String name = "show";
    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(){
        return collectionManager.show();
    }

    @Override
    public String getDescription(){
        return "show: Выводит в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public String getName(){
        return name;
    }
}


