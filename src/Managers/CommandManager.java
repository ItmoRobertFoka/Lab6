package Managers;

import Commands.Command;

import java.util.HashMap;

public class CommandManager {
    public HashMap<String, Command> commandMap = new HashMap<>();

    public void registerCommand(Command command){
        commandMap.put(command.getName(), command);
    }

    public CommandManager(){
        registerCommand();
    }
}
