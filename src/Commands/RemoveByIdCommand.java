package Commands;

import Managers.CollectionManager;
import java.util.Scanner;

public class RemoveByIdCommand implements Command {
    private final String name = "remove by id";
    private CollectionManager collectionManager;
    private Scanner scanner;

    public RemoveByIdCommand(CollectionManager collectionManager, Scanner scanner){
        this.collectionManager = collectionManager;
        this.scanner = scanner;
    }

    @Override
    public String execute(){
        System.out.println("Введите id фильма, которого вы хотите удалить: ");
        Integer removeId;
        while (true) {
            try {
                removeId = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка, введите корректное число");
            }
        }
        if (collectionManager.remove_by_id(removeId) == true){
            return "Фильм с id: " + removeId + " удален";
        }
        return "Фильма с таким id нет в коллекции";
    }

    @Override
    public String getDescription(){
        return ("remove_by_id: Удаляет фильм по id");
    }

    @Override
    public String getName(){
        return name;
    }
}
