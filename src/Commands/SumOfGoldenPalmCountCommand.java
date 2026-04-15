package Commands;

import Managers.CollectionManager;

/**
 * Команда, которая суммирует все значения поля golden_palm_count
 * у всех фильмов в коллекции.
 */
public class SumOfGoldenPalmCountCommand implements Command {
    private String name = "sum_of_golden_palm_count";
    private CollectionManager collectionManager;

    public SumOfGoldenPalmCountCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(){
        return "Сумма Золотых пальм: " + Long.toString(collectionManager.sum_of_golden_palm_count());
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
