package server.managers;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import common.Movie;
import common.MovieWrapper;
import common.Person;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


/**
 * Управляет коллекцией.
 * Предоставляет операции над коллекцией.
 */

public class CollectionManager {
    private List<Movie> movieList = new LinkedList<>();
    LocalDate creationMovieListDate = LocalDate.now();
    String fileName;
    XmlManager xmlManager;
    int currentId;


    public CollectionManager(String fileName) {
        this.fileName = fileName;
        xmlManager = new XmlManager(fileName,movieList);
        currentId = xmlManager.getCurrentId();
    }


    public int generateId(){
        return currentId++;
    }

    public void loadFromFile(String fileName) {
        xmlManager.loadFromFile(fileName);
        this.currentId = xmlManager.getCurrentId();
    }


    public String info(){
        return "Тип коллекции: " + movieList.getClass().getName() + "\n" +
                "Дата инициализации: " + creationMovieListDate + "\n" +
                "Количество элементов: " + movieList.size();
    }

    public String show() {
        if (movieList.isEmpty()) {
            return "Коллекция пуста";
        }
        return movieList.stream()
                .map(Movie::toString)
                .collect(Collectors.joining("\n", "\n", ""));
    }

    public void add(Movie movie){
        movie.setId(generateId());
        movieList.add(movie);
    }

        public String remove_by_id(int id){
            boolean idInCollection = movieList.stream()
                    .anyMatch(movie -> movie.getId() == id);

            if (idInCollection) {
                movieList = movieList.stream()
                        .filter(movie -> movie.getId() != id)
                        .collect(Collectors.toCollection(() -> new LinkedList<>()));
                return "Фильм с id = " + id + " удален";
            } else {
                return "Фильма с id = "  + id + " нет в коллекции";
            }
        }

        public boolean checkId(int id) {
            return movieList.stream()
                    .anyMatch(movie -> movie.getId() == id);
        }

        public boolean updateId(int id, Movie newMovie) {
            if (checkId(id) == true) {
                movieList =movieList.stream()
                        .filter(movie -> movie.getId() != id)
                        .collect(Collectors.toCollection(() -> new LinkedList<Movie>()));
                newMovie.setId(id);
                movieList.add(newMovie);
                return true;
            } else {
                return false;
            }
        }

        public void clear(){
            movieList.clear();
        }

        public String save() {
            if (fileName == null) {
                return "Ошибка, имя файла не задано";
            }
            try (FileOutputStream fileOut = new FileOutputStream(fileName);
                 BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOut)) {
                MovieWrapper movieWrapper = new MovieWrapper(movieList);
                XmlMapper mapper = new XmlMapper();
                mapper.findAndRegisterModules();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(bufferedOut, movieWrapper);
                return "Файл успешно сохранен";
            } catch (IOException e) {
                return "Ошибка сохранения файла: " +  e.getMessage();
            }
        }

        public void exit(){
            System.exit(0);

        }

        public String head(){
            return movieList.stream()
                    .findFirst()
                    .map(movie -> movie.toString())
                    .orElse("Коллекция пуста");
        }

        public boolean add_if_min(Movie newMovie) {
            if (movieList.isEmpty()) {
                movieList.add(newMovie);
                return true;
            }
            Movie minMovie = movieList.stream()
                    .min(Movie::compareTo)
                    .orElse(null);

            if (newMovie.compareTo(minMovie) < 0) {
                movieList.add(newMovie);
                return true;
            }
            return false;
        }

        public String remove_greater(Movie anotherMovie){
            movieList = movieList.stream()
                    .filter(movie -> movie.compareTo(anotherMovie) <= 0)
                    .collect(Collectors.toCollection(() -> new LinkedList<>()));
            return "Все фильмы, значение которых больше заданного, были удалены";
        }


        public long sum_of_golden_palm_count(){
            return movieList.stream()
                    .mapToLong((movie) -> movie.getGoldenPalmCount())
                    .sum();
        }

        public int count_greater_than_director(Person director){
            return (int) movieList.stream().
                    filter(movie -> movie.getDirector().compareTo(director) > 0)
                    .count();
        }

        public String filter_contains_name(String name){
            if (movieList == null || movieList.isEmpty()) {
                return "Список пустой";
            } else {
                return movieList.stream()
                        .filter(movie -> movie.getName().contains(name))
                        .map(movie -> movie.getName())
                        .collect(Collectors.joining("\n"));
            }
        }


        public List<Movie> getCollection() {
            return movieList;
        }
    }


