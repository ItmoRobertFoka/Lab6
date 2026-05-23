package Server.Commands;

import Server.CollectionManager;

/**
 * Команда, удаляющая все элементы из коллекции.
 */
public class ClearCommand implements Command {
    private final String name = "clear";
    private CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(){
        collectionManager.clear();
        return "Коллекция очищена";
    }

    @Override
    public String getDescription(){
        return "clear: Очищает коллекцию";
    }

    @Override
    public String getName(){
        return name;
    }
}
