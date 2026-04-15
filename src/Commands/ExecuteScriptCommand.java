package Commands;

import Managers.InputManager;

/**
 * Команда, которая выполняет скрипт из заданного файла.
 */
public class ExecuteScriptCommand implements Command {

    private final InputManager inputManager;

    public ExecuteScriptCommand(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    @Override
    public String execute() {
        try {
            System.out.println("Введите имя файла скрипта:");
            String fileName = inputManager.nextLine();

            inputManager.pushFile(fileName);

            return "Скрипт запущен";

        } catch (Exception e) {
            return "Ошибка выполнения скрипта: " + e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "execute_script: выполняет команды из файла";
    }
}