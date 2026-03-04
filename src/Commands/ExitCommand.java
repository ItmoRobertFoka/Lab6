package Commands;

import Managers.CollectionManager;

public class ExitCommand implements Command {
    private String name = "exit";
    private CollectionManager collectionManager;

    public ExitCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(){
        collectionManager.exit();
        return "Программа завершена без сохранения";
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

