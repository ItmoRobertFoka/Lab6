package Server.Commands;

import Server.CollectionManager;

/**
 * Команда, которая завершает программу без сохранения изменений.
 */
public class ExitCommand implements Command {
    private final String name = "exit";
    private CollectionManager collectionManager;

    public ExitCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(){
        collectionManager.exit();
        return "";
    }

    @Override
    public String getDescription(){
        return "exit: Завершает программу без сохранения";
    }

    @Override
    public String getName(){
        return name;
    }
}

