package Commands;

import Managers.CollectionManager;
import Managers.InputManager;

/**
 * Команда, которая выводит элементы, значение поля name которых содержит заданную подстроку
 */
public class FilterContainsNameCommand implements Command {
    private final String name = "filter_contains_name";
    private CollectionManager collectionManager;
    private InputManager inputManager;

    public FilterContainsNameCommand(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    @Override
    public String execute() {
        return collectionManager.filter_contains_name(inputManager.nextLine().trim());
    }

    @Override
    public String getDescription() {
        return "filter_contains_name: выводит элементы, значение поля name которых содержит заданную подстроку";
    }

    @Override
    public String getName() {
        return name;
    }
}
