package Managers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;


import Input.MovieMaker;
import Model.Movie;
import Model.MovieWrapper;
import Model.Person;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


/**
 * Управляет коллекцией.
 * Предоставляет операции над коллекцией.
 */

public class CollectionManager {
    public LinkedList<Movie> movieList = new LinkedList<>();
    LocalDate creationMovieListDate = LocalDate.now();

    String fileName;
    MovieMaker movieMaker;

    public CollectionManager(String fileName, InputManager inputManager) {
        this.fileName = fileName;
        this.movieMaker = new MovieMaker(inputManager);
    }

    private boolean containsId(int id) {
        for (Movie m : movieList) {
            if (m.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void loadFromFile(String fileName) {

        try {
            Scanner scanner = new Scanner(new File(fileName));
            StringBuilder xml = new StringBuilder();

            while (scanner.hasNextLine()) {
                xml.append(scanner.nextLine()).append("\n");
            }

            XmlMapper mapper = new XmlMapper();
            mapper.findAndRegisterModules();

            MovieWrapper wrapper = mapper.readValue(xml.toString(), MovieWrapper.class);

            if (wrapper == null || wrapper.getMovies() == null) {
                System.out.println("Файл пустой или битый");
                return;
            }

            MovieValidator validator = new MovieValidator();

            for (Movie m : wrapper.getMovies()) {

                if (m == null) continue;

                if (!validator.validate(m)) {
                    System.out.println("INVALID: " + m.getName());
                    continue;
                }

                if (containsId(m.getId())) continue;

                movieList.add(m);
            }

            updateCurrentId();

            System.out.println("Коллекция загружена");

        } catch (Exception e) {
            e.printStackTrace(); // ВАЖНО
        }
    }


    int currentId = 0;

    public void updateCurrentId() {
        int max = 0;
        for (Movie movie : movieList) {
            if (movie.getId() > max) {
                max = movie.getId();
            }
        }
        currentId = max + 1;
    }

    public int generateId(){
        return currentId++;
    }

    public String info(){
        return "Тип коллекции: " + movieList.getClass().getName() + "\n" +
                "Дата инициализации: " + creationMovieListDate + "\n" +
                "Количество элементов: " + movieList.size();
    }

    public String show(){
        if (movieList.isEmpty()){
            return "Коллекция пуста";
        }

        StringBuilder result = new StringBuilder();
        for (Movie movie : movieList){
            result.append(movie.toString()).append("\n");
        }
        return "\n" + result.toString();
    }

    public void add(Movie movie){
        movie.setId(generateId());
        movieList.add(movie);
    }

    public boolean remove_by_id(int id){
        for(int i = 0; i < movieList.size(); i++){
            if (movieList.get(i).getId() == id){
                movieList.remove(i);
                return true;
            }
        }
        return false;
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
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileName));
            MovieWrapper movieWrapper = new MovieWrapper(movieList);
            XmlMapper mapper = new XmlMapper();
            mapper.findAndRegisterModules();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(bufferedOutputStream, movieWrapper);
            return "Файл успешно сохранен";
        } catch (IOException e) {
            return "Ошибка сохранения файла: " + e.getMessage();
        }
    }

    public void exit(){
        System.exit(0);

    }

    public  String head(){
        if (movieList == null || movieList.isEmpty()){
            return "Коллекция пуста";
        } else {
            return movieList.getFirst().toString();
        }
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
        if (movieList.isEmpty()){
            return "Список уже пустой";
        } else {
            movieList.removeIf(movie -> movie.compareTo(anotherMovie) > 0);
            return "Все фильмы, значение которых больше заданного, были удалены";
        }
    }

    public long sum_of_golden_palm_count(){
        long palm_count = 0L;
        for (Movie movie : movieList){
            palm_count += movie.getGoldenPalmCount();
        }
        return palm_count;
    }

    public int count_greater_than_director(Person director){
        int count = 0;
        if (movieList.isEmpty()) {
            return 0;
        }
        for (Movie movie : movieList){
            if (movie.getDirector().compareTo(director) > 0){
                count++;
            }
        }
        return count;
    }

    public String filter_contains_name(String name){
        if (movieList == null || movieList.isEmpty()) {
            return "Список пустой";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (Movie movie : movieList){
                if (movie.getName().contains(name)){
                    stringBuilder.append(movie.getName());
                    stringBuilder.append("\n");
                }
            }
            return "Фильмы содержащие заданную подстроку: " + stringBuilder;
        }
    }
}


