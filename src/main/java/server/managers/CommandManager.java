package server.managers;


import server.commands.AddCommand;
import server.commands.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Управляет регистрацией и управлением команд.
 * Хранит все доступные команды приложения.
 */
public class CommandManager {
    public Map<String, Command> commandMap = new HashMap<>();

    public void registerCommand(Command command){
        commandMap.put(command.getName().toLowerCase(), command);
    }

    public CommandManager(CollectionManager collectionManager){
        registerCommand(new HelpCommand(this));
        registerCommand(new InfoCommand(collectionManager));
        registerCommand(new ShowCommand(collectionManager));
        registerCommand(new AddCommand(collectionManager));
        registerCommand(new UpdateIdCommand(collectionManager));
        registerCommand(new RemoveByIdCommand(collectionManager));
        registerCommand(new ClearCommand(collectionManager));
        registerCommand(new ExecuteScriptCommand());
        registerCommand(new HeadCommand(collectionManager));
        registerCommand(new AddIfMinCommand(collectionManager));
        registerCommand(new RemoveGreaterCommand(collectionManager));
        registerCommand(new SumOfGoldenPalmCountCommand(collectionManager));
        registerCommand(new CountGreaterThanDirectorCommand(collectionManager));
        registerCommand(new FilterContainsNameCommand(collectionManager));
    }

    public Map<String, Command> getCommandMap() {
        return commandMap;
    }
}


