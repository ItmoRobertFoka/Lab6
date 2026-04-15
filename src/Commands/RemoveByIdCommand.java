package Commands;

import Managers.CollectionManager;
import Managers.InputManager;

/**
 * Команда, которая удаляет фильм из коллекции по его id.
 * Если в коллекции не было фильма с заданным id, коллекция останется без изменений
 */
public class RemoveByIdCommand implements Command {
    private final String name = "remove_by_id";
    private CollectionManager collectionManager;
    private InputManager inputManager;

    public RemoveByIdCommand(CollectionManager collectionManager, InputManager inputManager){
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    @Override
    public String execute(){
        System.out.println("Введите id фильма, которого вы хотите удалить: ");
        Integer removeId;
        while (true) {
            try {
                removeId = Integer.parseInt(inputManager.nextLine().trim());
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
