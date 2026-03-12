package Commands;

import Managers.CollectionManager;

import java.util.Scanner;

public class FilterContainsNameCommand implements Command {
    private final String name = "filter_contains_name";
    private CollectionManager collectionManager;
    private Scanner scanner;

    public FilterContainsNameCommand(CollectionManager collectionManager, Scanner scanner) {
        this.collectionManager = collectionManager;
        this.scanner = scanner;
    }

    @Override
    public String execute() {
        System.out.println("Введите название фильма: ");
        return collectionManager.filter_contains_name(scanner.nextLine().trim());
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
