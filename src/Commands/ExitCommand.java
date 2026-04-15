package Commands;

import Managers.CollectionManager;

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
        System.out.println( "Программа завершена без сохранения");
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

