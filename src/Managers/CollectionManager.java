package Managers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import Model.Movie;
import Model.Person;

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

    public String help(){

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

    public void remove_by_id(int id){
        for(int i = 0; i < movieList.size(); i++){
            if (movieList.get(i).getId() == id){
                movieList.remove(i);
                return;
            }
        }
    }

    public void updateId(Movie movie){
        for(int i = 0; i < movieList.size(); i++){
            if (movieList.get(i).getId() == movie.getId()){
                movieList.set(i, movie);
                return;
            }
        }
    }

    public void clear(){
        movieList.clear();
    }

    //save
    //executeScript

    public void exit(){
        System.exit(0);

    }

    public  Movie head(){
        return movieList.getFirst();
        }

    public void add_if_min(Movie movie){
        if (movieList.isEmpty()){
            movie.setId(generateId());
            movieList.add(movie);
        } else {
            Movie minMovie = Collections.min(movieList);
            if (movie.compareTo(minMovie) < 0){
                movie.setId(generateId());
                movieList.add(movie);
            }
        }
    }

    public void remove_greater(Movie anotherMovie){
        if (movieList.isEmpty()){
            return;
        } else {
            movieList.removeIf(movie -> movie.compareTo(anotherMovie) > 0);
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
        for (Movie movie : movieList){
            if (movie.getDirector().compareTo(director) > 0){
                count++;
            }
        }
        return count;
    }

    public String filter_contains_name(String name){
        if (movieList == null) {
            return "Список пустой";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (Movie movie : movieList){
                if (movie.getName().contains(name)){
                    stringBuilder.append(movie.getName());
                    stringBuilder.append("\n");
                }
            }
            return stringBuilder.toString();
        }
    }
}


