package Managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

import Model.Movie;
import Model.Person;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class CollectionManager {
    public LinkedList<Movie> movieList = new LinkedList<>();
    LocalDate creationMovieListDate = LocalDate.now();


    private int currentId = 1;

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
            result.append(movie.getName()).append("\n");
        }
        return result.toString();
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

    public String updateId(Movie movie){
        for(int i = 0; i < movieList.size(); i++){
            if (movieList.get(i).getId() == movie.getId()){
                movieList.set(i, movie);
                return "Фильм с id: " + movie.getId() + " из коллекции успешно заменен";
            }
        }
        return "В коллекции нет фильма с id: " + movie.getId();
    }

    public void clear(){
        movieList.clear();
    }

    //save
    //executeScript

    public void exit(){
        System.exit(0);

    }

    public  String head(){
        if (movieList == null){
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


