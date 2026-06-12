package server.managers;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Movie;
import common.Person;

/**
 * Управляет коллекцией фильмов в оперативной памяти.
 * Полностью потокобезопасен и синхронизирован с базой данных.
 */
public class CollectionManager {
    private static final Logger logger = LoggerFactory.getLogger(CollectionManager.class);

    private List<Movie> movieList = new LinkedList<>();
    private final LocalDate creationMovieListDate = LocalDate.now();
    private final MovieDatabaseManager movieDbManager;

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public CollectionManager(MovieDatabaseManager movieDbManager) {
        this.movieDbManager = movieDbManager;
        loadFromDatabase();
    }

    public void loadFromDatabase() {
        writeLock.lock();
        try {
            movieList.clear();
            movieList.addAll(movieDbManager.loadCollection());
            logger.info("Коллекция успешно инициализирована из БД. Загружено элементов: " + movieList.size());
        } finally {
            writeLock.unlock();
        }
    }

    public String info() {
        readLock.lock();
        try {
            return "Тип коллекции: " + movieList.getClass().getName() + "\n" +
                    "Дата инициализации: " + creationMovieListDate + "\n" +
                    "Количество элементов: " + movieList.size();
        } finally {
            readLock.unlock();
        }
    }

    public String show() {
        readLock.lock();
        try {
            if (movieList.isEmpty()) {
                return "Коллекция пуста";
            }
            return movieList.stream()
                    .map(Movie::toString)
                    .collect(Collectors.joining("\n", "\n", ""));
        } finally {
            readLock.unlock();
        }
    }

    public void add(Movie movie, String ownerLogin) {
        writeLock.lock();
        try {
            int generatedId = movieDbManager.insertMovie(movie, ownerLogin);

            movie.setId(generatedId);
            movie.setOwnerLogin(ownerLogin);

            movieList.add(movie);
        } catch (Exception e) {
            logger.error("Критическая ошибка при добавлении фильма", e);
            throw new RuntimeException("Не удалось сохранить фильм в базу данных.");
        } finally {
            writeLock.unlock();
        }
    }

    public String remove_by_id(int id, String ownerLogin) {
        writeLock.lock();
        try {
            boolean deletedInDb = movieDbManager.deleteMovie(id, ownerLogin);

            if (deletedInDb) {
                movieList = movieList.stream()
                        .filter(movie -> movie.getId() != id)
                        .collect(Collectors.toCollection(LinkedList::new));
                return "Фильм с id = " + id + " удален";
            } else {
                boolean idInCollection = movieList.stream().anyMatch(movie -> movie.getId() == id);
                if (idInCollection) {
                    return "Ошибка: Вы не являетесь владельцем фильма с id = " + id + " и не можете его удалить!";
                } else {
                    return "Фильма с id = " + id + " нет в коллекции";
                }
            }
        } catch (Exception e) {
            return "Ошибка базы данных при удалении: " + e.getMessage();
        } finally {
            writeLock.unlock();
        }
    }

    public boolean checkId(int id) {
        readLock.lock();
        try {
            return movieList.stream()
                    .anyMatch(movie -> movie.getId() == id);
        } finally {
            readLock.unlock();
        }
    }

    public boolean updateId(int id, Movie newMovie, String ownerLogin) {
        writeLock.lock();
        try {
            boolean updatedInDb = movieDbManager.updateMovie(id, newMovie, ownerLogin);

            if (updatedInDb) {
                movieList = movieList.stream()
                        .filter(movie -> movie.getId() != id)
                        .collect(Collectors.toCollection(LinkedList::new));

                newMovie.setId(id);
                newMovie.setOwnerLogin(ownerLogin);
                movieList.add(newMovie);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("Ошибка при обновлении фильма с id = " + id, e);
            return false;
        } finally {
            writeLock.unlock();
        }
    }

    public String clear(String ownerLogin) {
        writeLock.lock();
        try {
            boolean success = movieDbManager.clearOwnedMovies(ownerLogin);

            if (success) {
                movieList = movieList.stream()
                        .filter(movie -> !movie.getOwnerLogin().equals(ownerLogin))
                        .collect(Collectors.toCollection(java.util.LinkedList::new));
                return "Все ваши фильмы успешно удалены из коллекции.";
            }
            return "Не удалось очистить ваши элементы в базе данных";
        } catch (Exception e) {
            return "Ошибка при очистке коллекции: " + e.getMessage();
        } finally {
            writeLock.unlock();
        }
    }

    public String head() {
        readLock.lock();
        try {
            return movieList.stream()
                    .findFirst()
                    .map(Movie::toString)
                    .orElse("Коллекция пуста");
        } finally {
            readLock.unlock();
        }
    }

    public boolean add_if_min(Movie newMovie, String ownerLogin) {
        writeLock.lock();
        try {
            if (movieList.isEmpty()) {
                add(newMovie, ownerLogin);
                return true;
            }
            Movie minMovie = movieList.stream()
                    .min(Movie::compareTo)
                    .orElse(null);

            if (minMovie != null && newMovie.compareTo(minMovie) < 0) {
                add(newMovie, ownerLogin);
                return true;
            }
            return false;
        } finally {
            writeLock.unlock();
        }
    }

    public String remove_greater(Movie anotherMovie, String ownerLogin) {
        writeLock.lock();
        try {
            List<Integer> idsToRemove = movieList.stream()
                    .filter(movie -> movie.getOwnerLogin().equals(ownerLogin))
                    .filter(movie -> movie.compareTo(anotherMovie) > 0)
                    .map(Movie::getId)
                    .collect(Collectors.toList());

            int deletedCount = 0;
            for (int id : idsToRemove) {
                if (movieDbManager.deleteMovie(id, ownerLogin)) {
                    deletedCount++;
                }
            }

            movieList = movieList.stream()
                    .filter(movie -> !idsToRemove.contains(movie.getId()))
                    .collect(Collectors.toCollection(LinkedList::new));

            return "Удалено ваших фильмов, превышающих заданный: " + deletedCount;
        } catch (Exception e) {
            return "Ошибка при выполнении remove_greater: " + e.getMessage();
        } finally {
            writeLock.unlock();
        }
    }

    public long sum_of_golden_palm_count() {
        readLock.lock();
        try {
            return movieList.stream()
                    .mapToLong(Movie::getGoldenPalmCount)
                    .sum();
        } finally {
            readLock.unlock();
        }
    }

    public int count_greater_than_director(Person director) {
        readLock.lock();
        try {
            return (int) movieList.stream()
                    .filter(movie -> movie.getDirector().compareTo(director) > 0)
                    .count();
        } finally {
            readLock.unlock();
        }
    }

    public String filter_contains_name(String name) {
        readLock.lock();
        try {
            if (movieList.isEmpty()) {
                return "Список пустой";
            } else {
                return movieList.stream()
                        .filter(movie -> movie.getName().contains(name))
                        .map(Movie::getName)
                        .collect(Collectors.joining("\n"));
            }
        } finally {
            readLock.unlock();
        }
    }

    public List<Movie> getCollection() {
        readLock.lock();
        try {
            return new LinkedList<>(movieList);
        } finally {
            readLock.unlock();
        }
    }
}