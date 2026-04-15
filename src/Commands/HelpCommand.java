package Commands;

import Managers.CommandManager;

/**
 * Команда, которая выводит справку по доступным командам.
 */
public class HelpCommand implements Command {
    private final String name = "help";
    private CommandManager commandManager;

    public HelpCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String execute() {
        return "Доступные команды: " + "\n" + commandManager.help();
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

