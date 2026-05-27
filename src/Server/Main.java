package Server;

import Server.Managers.CollectionManager;
import Server.Managers.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(Main.class);
        int port = 1234;
        String datafile = System.getenv("MOVIE_FILE");
        if (datafile == null || datafile.isEmpty()) {
            datafile = "Movies.xml";
        }
        logger.info("Запуск сервера приложения...");
        logger.info("Инициализация бизнес-логики (файл данных: {})", datafile);

        CollectionManager collectionManager = new CollectionManager(datafile);
        collectionManager.loadFromFile(datafile);
        CommandManager commandManager = new CommandManager(collectionManager);
        UdpServer server = new UdpServer(port, commandManager, collectionManager);

        // ФОНОВЫЙ ПОТОК ДЛЯ КОНСОЛИ СЕРВЕРА
        Thread consoleThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            logger.info("Консоль сервера активна. Доступные команды: save, exit");

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim().toLowerCase();

                if (line.equals("save")) {
                    String status = collectionManager.save();
                    logger.info("[КОНСОЛЬ СЕРВЕРА] Результат: {}", status);
                }
                else if (line.equals("exit")) {
                    logger.info("[КОНСОЛЬ СЕРВЕРА] Получена команда exit. Сохраняем коллекцию...");
                    collectionManager.save();
                    logger.info("[КОНСОЛЬ СЕРВЕРА] Завершение работы. Пока!");
                    System.exit(0);
                }
                else if (!line.isEmpty()) {
                    logger.warn("[КОНСОЛЬ СЕРВЕРА] Неизвестная команда '{}'. Доступно: save, exit", line);
                }
            }
        });
        consoleThread.setDaemon(true); // Чтобы поток завершался вместе с приложением
        consoleThread.start();

        logger.info("Передача управления сетевому слою. Сервер слушает порт {}", port);
        server.start();
    }
}