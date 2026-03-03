package Managers;

import Commands.AddCommand;
import Commands.Command;

import java.util.HashMap;

public class CommandManager {
    private HashMap<String, Command> commandMap = new HashMap<>();

    public void registerCommand(Command command){
        commandMap.put(command.getName(), command);
    }

    public CommandManager(){
        registerCommand(new AddCommand());
    }
}
