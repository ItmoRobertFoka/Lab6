package Commands;

import Managers.CollectionManager;

public class sum_of_golden_palm_countCommand implements Command {
    private String name = "sum_of_golden_palm_count";
    private CollectionManager collectionManager;

    public sum_of_golden_palm_countCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(){
        return Long.toString(collectionManager.sum_of_golden_palm_count());
    }

    @Override
    public String getDescription(){
        return "sum_of_golden_palm_count: Выводит сумму \"Золотых пальм\" всех элеметов коллекции";
    }

    @Override
    public String getName(){
        return name;
    }
}
