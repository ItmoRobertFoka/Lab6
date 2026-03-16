import Managers.CollectionManager;
import Managers.CommandManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filmName = System.getenv("MOVIE_FILE");

        CollectionManager collectionManager = new CollectionManager();
        Scanner userScanner = new Scanner(System.in);
        CommandManager commandManager = new CommandManager(collectionManager, userScanner);

        if (filmName == null) {
            System.out.println("Ошибка, переменная окружения MOVIE_FILE не задана, коллекция пустая");;
        } else {
            collectionManager.loadFromFile(filmName);
        }

        while (true) {
            System.out.println("\n" + "Введите команду: ");
            String input = userScanner.nextLine().trim();
            if (commandManager.commandMap.containsKey(input)){
                System.out.println(commandManager.executeCommand(input));
            } else {
                System.out.println("Введите команду из доступных");
            }
        }
    }
}