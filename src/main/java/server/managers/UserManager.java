package server.managers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.PasswordHasher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {
    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);
    private final DatabaseConnectionManager connectionManager;

    public UserManager(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public boolean validateUser(String login, String password) {
        if (login == null || password == null || login.isEmpty()) return false;
        String query = "SELECT password_hash FROM lab7_users WHERE login = ?;";

        try (PreparedStatement ps = connectionManager.getConnection().prepareStatement(query)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    return storedHash.equals(PasswordHasher.hash(password));
                }
            }
        } catch (SQLException e) {
            logger.error("Ошибка при валидации пользователя: " + login, e);
        }
        return false;
    }

    public boolean registerUser(String login, String password) {
        if (login == null || password == null || login.isEmpty() || password.isEmpty()) return false;
        String query = "INSERT INTO lab7_users (login, password_hash) VALUES (?, ?);";

        try (PreparedStatement ps = connectionManager.getConnection().prepareStatement(query)) {
            ps.setString(1, login);
            ps.setString(2, PasswordHasher.hash(password));
            ps.executeUpdate();
            logger.info("В базе данных зарегистрирован пользователь: " + login);
            return true;
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                logger.warn("Попытка повторной регистрации логина: " + login);
            } else {
                logger.error("Ошибка регистрации пользователя", e);
            }
            return false;
        }
    }
}