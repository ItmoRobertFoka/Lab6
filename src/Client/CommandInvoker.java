package Client;

import Server.Commands.Command;
import Server.CommandManager;

public class CommandInvoker {
    private InputManager inputManager;
    private CommandManager commandManager;

    public CommandInvoker(InputManager inputManager, CommandManager commandManager) {
        this.inputManager = inputManager;
        this.commandManager = commandManager;
    }

    public String executeCommand(String line) {
        String[] parts = line.trim().split("\\s+");
        String commandName = parts[0];
        String args = null;
        if (parts.length > 1) {
            args = line.substring(commandName.length()).trim();
            inputManager.setBufferedLine(args);
        }
        Command command = commandManager.getCommandMap().get(commandName);
        if (command == null) {
            return "Команда не найдена";
        }
        return command.execute();
    }
}
