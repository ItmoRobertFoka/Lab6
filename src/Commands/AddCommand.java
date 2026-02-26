package Commands;

import Model.Movie;

public class AddCommand implements Command{
    private CollectionManager collectionManager;
    private Movie movie;

    public AddCommand(CollectionManager collectionManager, Movie movie){
        this.collectionManager = collectionManager;
        this.movie = movie;
    }

    @Override
    public String execute(){
        collectionManager.add(movie);
        return "Фильм добавлен в коллекцию";
    }

    @Override
    public String getDescription(){
        return "add: добавляет новый элемент в коллекцию";
    }
}
