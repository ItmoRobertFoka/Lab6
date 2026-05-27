package Server.Commands;

import Common.Request;
import Common.Response;
import Server.Managers.CommandManager;

import java.io.Serializable;

/**
 * Команда, которая выводит справку по доступным командам.
 */
public class HelpCommand implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name = "help";
    private CommandManager commandManager;

    public HelpCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public Response execute(Request request) {
        StringBuilder sb = new StringBuilder();
        for (Command command : commandManager.getCommandMap().values()) {
            sb.append(command.getDescription()).append("\n");
        }
        return new Response(true, sb.toString().trim(), null);
    }

    @Override
    public String getDescription() {
        return "help: Выводит справку по доступным командам";
    }

    @Override
    public String getName() {
        return name;
    }
}

