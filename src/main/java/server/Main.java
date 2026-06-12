package server;

import server.managers.CollectionManager;
import server.managers.CommandManager;
import server.managers.DatabaseConnectionManager;
import server.managers.MovieDatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        int port = 1234;
        logger.info("Запуск сервера приложения...");

        DatabaseConnectionManager databaseConnectionManager = new DatabaseConnectionManager();
        MovieDatabaseManager movieDatabaseManager = new MovieDatabaseManager(databaseConnectionManager);

        CollectionManager collectionManager = new CollectionManager(movieDatabaseManager);
        collectionManager.loadFromDatabase();
        CommandManager commandManager = new CommandManager(collectionManager);

        UdpServer server = new UdpServer(port, commandManager, collectionManager, movieDatabaseManager);

        server.start();
        server.start();
    }
}