package Server;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


import Client.MovieMaker;
import Common.Movie;
import Common.MovieWrapper;
import Common.Person;
import Client.InputManager;
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
    MovieMaker movieMaker;
    XmlManager xmlManager = new XmlManager(fileName, movieList);
    int currentId = xmlManager.getCurrentId();


    public CollectionManager(String fileName, InputManager inputManager) {
        this.fileName = fileName;
        this.movieMaker = new MovieMaker(inputManager);
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

    public boolean updateId(int id) {

        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getId() == id) {
                Movie newMovie = movieMaker.createMovie();
                newMovie.setId(id);
                movieList.set(i, newMovie);
                return true;
            }
        }

        return false;
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
            return "Ошибка сохранения файла: " + e.getMessage();
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

    public String add_if_min() {
        return add_if_min(movieMaker.createMovie());
    }

    public String add_if_min(Movie movie){
        if (movieList.isEmpty()){
            movie.setId(generateId());
            movieList.add(movie);
            return "Фильм успешно добавлен";
        } else {
            Movie minMovie = Collections.min(movieList);
            if (movie.compareTo(minMovie) < 0){
                movie.setId(generateId());
                movieList.add(movie);
                return "Фильм успешно добавлен";
            }
            return "Фильм не добавлен, так как не является минимальным";
        }
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


