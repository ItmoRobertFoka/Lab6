package Server.Commands;

import Server.CollectionManager;

/**
 * Команда, которая выводит информацию о коллекции
 */
public class InfoCommand implements Command {
    private final String name = "info";
    private CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute() {
        return collectionManager.info();
    }

    @Override
    public String getDescription(){
        return "info: Выводит в стандартный поток информацию о коллекции";
    }

    @Override
    public String getName(){
        return name;
    }
}
