package Server.Commands;

import Server.CollectionManager;

/**
 * Команда, которая добавляет фильм в коллецию,
 * если его значение меньше минимального.
 */
public class AddIfMinCommand implements Command {
    private final String name = "add_if_min";
    private final CollectionManager collectionManager;

    public AddIfMinCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute() {
        return collectionManager.add_if_min();
    }

    @Override
    public String getDescription() {
        return "add_if_min: Добавляет фильм в коллекцию если его значение меньше чем у минимального в коллекции";
    }

    @Override
    public String getName() {
        return name;
    }
}
