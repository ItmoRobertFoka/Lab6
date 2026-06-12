package server.managers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionManager.class);

    String URL = "jdbc:postgresql://localhost:5432/studs";
    private final String user;
    private final String password;

    public DatabaseConnectionManager() {
        this.user = System.getenv("DB_USER");
        this.password = System.getenv("DB_PASS");

        if (user == null || password == null) {
            logger.error("Критическая ошибка: Переменные окружения DB_USER или DB_PASS не установлены!");
            throw new RuntimeException("Настройте переменные окружения перед запуском сервера.");
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Драйвер PostgreSQL не найден!", e);
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, user, password);
    }
}