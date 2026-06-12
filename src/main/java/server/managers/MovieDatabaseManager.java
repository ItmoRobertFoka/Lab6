package server.managers;

import common.*;
import server.PasswordHasher;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MovieDatabaseManager {
    private final DatabaseConnectionManager connectionManager;
    private static final Logger logger = LoggerFactory.getLogger(MovieDatabaseManager.class);

    public MovieDatabaseManager(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public boolean checkUserCredentials(String login, String rawPassword) {
        String hashedPassword = PasswordHasher.hash(rawPassword);
        String sql = "SELECT COUNT(*) FROM lab7_users WHERE login = ? AND password_hash = ?";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, login);
            pstmt.setString(2, hashedPassword);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registerUser(String login, String rawPassword) {
        String hashedPassword = PasswordHasher.hash(rawPassword);
        String sql = "INSERT INTO lab7_users (login, password_hash) VALUES (?, ?)";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, login);
            pstmt.setString(2, hashedPassword);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<Movie> loadCollection() {
        List<Movie> list = new LinkedList<>();

        String sql = "SELECT m.id AS movie_id, m.name AS movie_name, m.oscars_count, m.golden_palm_count, " +
                "       m.genre, m.mpaa_rating, m.owner_login, " +
                "       c.x AS coord_x, c.y AS coord_y, " +
                "       p.name AS dir_name, p.birthday AS dir_birthday, p.hair_color AS dir_hair_color, p.nationality AS dir_nationality, " +
                "       l.x AS loc_x, l.y AS loc_y, l.name AS loc_name " +
                "FROM lab7_movies m " +
                "LEFT JOIN lab7_coordinates c ON m.coordinates_id = c.id " +
                "LEFT JOIN lab7_persons p ON m.director_id = p.id " +
                "LEFT JOIN lab7_locations l ON p.location_id = l.id";

        try (Connection conn = connectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Coordinates coordinates = null;
                if (rs.getObject("coord_x") != null) {
                    float coordX = rs.getFloat("coord_x");
                    double coordY = rs.getDouble("coord_y");
                    coordinates = new Coordinates(coordX, coordY);
                }

                Location location = null;
                if (rs.getObject("loc_x") != null) {
                    double locX = rs.getDouble("loc_x");
                    float locY = rs.getFloat("loc_y");
                    String locName = rs.getString("loc_name");
                    location = new Location(locX, locY, locName);
                }

                Person director = null;
                if (rs.getString("dir_name") != null) {
                    String dirName = rs.getString("dir_name");
                    java.sql.Date bdaySql = rs.getDate("dir_birthday");
                    java.time.LocalDate birthday = (bdaySql != null) ? bdaySql.toLocalDate() : null;

                    String hairColorStr = rs.getString("dir_hair_color");
                    Color hairColor = (hairColorStr != null) ? Color.valueOf(hairColorStr) : null;

                    String natStr = rs.getString("dir_nationality");
                    Country nationality = (natStr != null) ? Country.valueOf(natStr) : null;

                    director = new Person(dirName, birthday, hairColor, nationality, location);
                }

                int movieId = rs.getInt("movie_id");
                String movieName = rs.getString("movie_name");
                int oscarCount = rs.getInt("oscars_count");
                long goldenPalmCount = rs.getLong("golden_palm_count");

                String genreStr = rs.getString("genre");
                MovieGenre movieGenre = (genreStr != null) ? MovieGenre.valueOf(genreStr) : null;

                String ratingStr = rs.getString("mpaa_rating");
                MpaaRating mpaaRating = (ratingStr != null) ? MpaaRating.valueOf(ratingStr) : null;

                String ownerLogin = rs.getString("owner_login");

                Movie movie = new Movie(movieName, coordinates, oscarCount, goldenPalmCount, movieGenre, mpaaRating, director);
                movie.setId(movieId);
                movie.setOwnerLogin(ownerLogin);

                list.add(movie);
            }
            logger.info("Коллекция успешно синхронизирована с БД. Загружено элементов: " + list.size());

        } catch (SQLException e) {
            logger.error("Критическая ошибка при чтении таблиц из БД: ", e);
        }

        return list;
    }

    public int insertMovie(Movie movie, String ownerLogin) throws SQLException {
        Connection conn = connectionManager.getConnection();
        conn.setAutoCommit(false);

        try {
            int locationId = -1;
            if (movie.getDirector().getLocation() != null) {
                String sqlLoc = "INSERT INTO lab7_locations (x, y, name) VALUES (?, ?, ?) RETURNING id";
                try (PreparedStatement ps = conn.prepareStatement(sqlLoc)) {
                    ps.setDouble(1, movie.getDirector().getLocation().getX());
                    ps.setDouble(2, movie.getDirector().getLocation().getY());
                    ps.setString(3, movie.getDirector().getLocation().getName());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) locationId = rs.getInt(1);
                    }
                }
            }

            int personId = -1;
            String sqlPerson = "INSERT INTO lab7_persons (name, birthday, hair_color, nationality, location_id) VALUES (?, ?, ?, ?, ?) RETURNING id";
            try (PreparedStatement ps = conn.prepareStatement(sqlPerson)) {
                ps.setString(1, movie.getDirector().getName());
                ps.setDate(2, java.sql.Date.valueOf(movie.getDirector().getBirthday()));
                ps.setString(3, movie.getDirector().getHairColor().name());
                ps.setString(4, movie.getDirector().getNationality().name());
                if (locationId != -1) ps.setInt(5, locationId); else ps.setNull(5, java.sql.Types.INTEGER);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) personId = rs.getInt(1);
                }
            }


            int coordId = -1;
            String sqlCoord = "INSERT INTO lab7_coordinates (x, y) VALUES (?, ?) RETURNING id";
            try (PreparedStatement ps = conn.prepareStatement(sqlCoord)) {
                ps.setDouble(1, movie.getCoordinates().getX());
                ps.setDouble(2, movie.getCoordinates().getY());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) coordId = rs.getInt(1);
                }
            }


            int movieId = -1;
            String sqlMovie = "INSERT INTO lab7_movies (name, owner_login, creation_date, oscars_count, golden_palm_count, genre, mpaa_rating, coordinates_id, director_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
            try (PreparedStatement ps = conn.prepareStatement(sqlMovie)) {
                ps.setString(1, movie.getName());
                ps.setString(2, ownerLogin);
                ps.setTimestamp(3, java.sql.Timestamp.valueOf(movie.getCreationDate()));
                ps.setInt(4, movie.getOscarsCount());
                ps.setLong(5, movie.getGoldenPalmCount());
                ps.setString(6, movie.getGenre() != null ? movie.getGenre().name() : null);
                ps.setString(7, movie.getMpaaRating() != null ? movie.getMpaaRating().name() : null);
                ps.setInt(8, coordId);
                ps.setInt(9, personId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) movieId = rs.getInt(1);
                }
            }

            conn.commit();
            return movieId;

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public boolean deleteMovie(int id, String ownerLogin) throws SQLException {
        String sql = "DELETE FROM lab7_movies WHERE id = ? AND owner_login = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, ownerLogin);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateMovie(int id, Movie movie, String ownerLogin) throws SQLException {
        String sql = "UPDATE lab7_movies SET name = ?, oscars_count = ?, genre = ? WHERE id = ? AND owner_login = ?";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, movie.getName());
            ps.setInt(2, movie.getOscarsCount());
            ps.setString(3, movie.getGenre().toString());
            ps.setInt(4, id);
            ps.setString(5, ownerLogin);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean clearOwnedMovies(String ownerLogin) throws SQLException {
        String sql = "DELETE FROM lab7_movies WHERE owner_login = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ownerLogin);
            ps.executeUpdate();
            return true;
        }
    }
}