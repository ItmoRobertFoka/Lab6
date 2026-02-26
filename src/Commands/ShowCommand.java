package Commands;

public class ShowCommand implements Command {
    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(){
        return collectionManager.show();
    }

    @Override
    public String getDescription(){
        return "show: выводит коллекцию фильмов";
    }
}
