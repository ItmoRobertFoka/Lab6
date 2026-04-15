import Managers.CollectionManager;
import Managers.CommandManager;
import Managers.InputManager;

public class Main {
    public static void main(String[] args) {
        String fileName = System.getenv("MOVIE_FILE");
        InputManager inputManager = new InputManager();
        CollectionManager collectionManager = new CollectionManager(fileName, inputManager);
        CommandManager commandManager = new CommandManager(collectionManager, inputManager);

        if (fileName == null) {
            System.out.println("Ошибка, переменная окружения MOVIE_FILE не задана, коллекция пустая");;
        } else {
            collectionManager.loadFromFile(fileName);
        }


        while (true) {
            System.out.println("\n" + "Введите команду: ");
            String input = inputManager.nextLine().trim();
            if (commandManager.commandMap.containsKey(input)){
                System.out.println(commandManager.executeCommand(input));
            } else {
                System.out.println("Введите команду из доступных");
            }
        }
    }
}