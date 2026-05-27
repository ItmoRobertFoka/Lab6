package Server.Commands;

import Client.MovieMaker;
import Common.Movie;
import Common.Request;
import Common.Response;
import Server.Managers.CollectionManager;

import java.io.Serializable;

/**
 * Команда, которая добавляет фильм в коллекцию.
 * Использует динамический ввод.
 */
public class AddCommand implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name = "add";
    private CollectionManager collectionManager;
    private MovieMaker movieMaker;

    public AddCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

   @Override
   public Response execute(Request request) {
        Movie movie = (Movie) request.getArgument();
        collectionManager.add(movie);
        return new Response(true, "Фильм успешно добавлен в коллекцию",null);
   }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public String getDescription(){
        return "add: Добавляет фильм в коллекцию";
    }
}
