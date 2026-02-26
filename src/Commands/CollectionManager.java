package Commands;

import java.time.LocalDateTime;
import java.util.LinkedList;
import Model.Movie;

public class CollectionManager {
    public LinkedList<Movie> movieList = new LinkedList<>();

    private int currentId = 1;

    public int generateId(){
        return currentId++;
    }

    // Help
    //info

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

    //updateID

    //remove_by_id

    public void clear(LinkedList<Movie> movieList){
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
    }

    //add_if_min
    //remove_greater
    //sum_of_gold_palm_count
    //count_greater_than_director director
    //filter_contains_name name

