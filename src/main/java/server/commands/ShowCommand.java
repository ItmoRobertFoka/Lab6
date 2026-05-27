package server.commands;

import common.Movie;
import common.Request;
import common.Response;
import server.managers.CollectionManager;
import server.MovieSizeComparator;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда, которая выводит все элементы коллекции в строковом представлении.
 */

public class ShowCommand implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name = "show";
    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        List<Movie> list = collectionManager.getCollection();

        if (list.isEmpty()) {
            return new Response(false, "Коллекция пуста", null);
        }

        List<Movie> sortedList = list.stream()
                .sorted(new MovieSizeComparator())
                .collect(Collectors.toList());

        return new Response(true, "Коллекция успешно отсортирована по размеру в байтах и получена", sortedList);
    }

    @Override
    public String getDescription(){
        return "show: Выводит в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public String getName(){
        return name;
    }
}


