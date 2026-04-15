package Managers;

import Commands.AddCommand;
import Commands.*;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Управляет регистрацией и управлением команд.
 * Хранит все доступные команды приложения.
 */
public class CommandManager {
    public HashMap<String, Command> commandMap = new HashMap<>();

    public void registerCommand(Command command){
        commandMap.put(command.getName(), command);
    }

    public CommandManager(CollectionManager collectionManager, InputManager inputManager){
        registerCommand(new HelpCommand(this));
        registerCommand(new InfoCommand(collectionManager));
        registerCommand(new ShowCommand(collectionManager));
        registerCommand(new AddCommand(collectionManager,inputManager));
        registerCommand(new UpdateIdCommand(collectionManager, inputManager));
        registerCommand(new RemoveByIdCommand(collectionManager,inputManager));
        registerCommand(new ClearCommand(collectionManager));
        registerCommand(new SaveCommand(collectionManager));
        registerCommand(new ExecuteScriptCommand(inputManager));
        registerCommand(new ExitCommand(collectionManager));
        registerCommand(new HeadCommand(collectionManager));
        registerCommand(new AddIfMinCommand(collectionManager,inputManager));
        registerCommand(new RemoveGreaterCommand(collectionManager,inputManager));
        registerCommand(new SumOfGoldenPalmCountCommand(collectionManager));
        registerCommand(new CountGreaterThanDirectorCommand(collectionManager, inputManager));
        registerCommand(new FilterContainsNameCommand(collectionManager, inputManager));
    }

    public String help() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Command command : commandMap.values()) {
            stringBuilder.append(command.getDescription() + "\n");
        }
        return stringBuilder.toString();
    }

    public String executeCommand(String command) {
        return commandMap.get(command).execute();
    }
}


