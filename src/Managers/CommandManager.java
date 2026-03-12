package Managers;

import Commands.AddCommand;
import Commands.*;

import java.util.HashMap;
import java.util.Scanner;

public class CommandManager {
    public HashMap<String, Command> commandMap = new HashMap<>();

    public void registerCommand(Command command){
        commandMap.put(command.getName(), command);
    }

    public CommandManager(CollectionManager collectionManager, Scanner scanner){
        registerCommand(new HelpCommand(this));
        registerCommand(new InfoCommand(collectionManager));
        registerCommand(new ShowCommand(collectionManager));
        registerCommand(new AddCommand(collectionManager,scanner));
        registerCommand(new UpdateIdCommand(collectionManager, scanner));
        registerCommand(new RemoveByIdCommand(collectionManager,scanner));
        registerCommand(new ClearCommand(collectionManager));
        //registerCommand(new SaveCommand());
        //registerCommand(new ExecuteScriptCommand());
        registerCommand(new ExitCommand(collectionManager));
        registerCommand(new HeadCommand(collectionManager));
        registerCommand(new AddIfMinCommand(collectionManager,scanner));
        registerCommand(new RemoveGreaterCommand(collectionManager,scanner));
        registerCommand(new SumOfGoldenPalmCountCommand(collectionManager));
        registerCommand(new CountGreaterThanDirectorCommand(collectionManager, scanner));
        registerCommand(new FilterContainsNameCommand(collectionManager, scanner));
    }

    public String help() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Command command : commandMap.values()) {
            stringBuilder.append(command.getDescription() + "\n");
        }
        return stringBuilder.toString();
    }
}


