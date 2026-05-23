package Server.Commands;

import Server.CollectionManager;

/**
 * Команда, которая сохраняет коллекцию в файл.
 */
public class SaveCommand implements Command {
    private final String name = "save";
    private CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute() {
        return collectionManager.save();
    }

    @Override
    public String getDescription() {
        return "save: Сохраняет коллекцию в файл";
    }

    @Override
    public String getName() {
        return name;
    }
}
