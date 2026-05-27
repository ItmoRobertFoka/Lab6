package Server.Commands;

import Common.Request;
import Common.Response;
import java.io.Serializable;
/**
 * Команда, которая выполняет скрипт из заданного файла.
 */
public class ExecuteScriptCommand implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    public ExecuteScriptCommand() {}

    @Override
    public Response execute(Request request) {
        try {
            String fileName = (String) request.getArgument();
            return new Response(true,"Скрипт запущен", null);

        } catch (Exception e) {
            return new Response(false,"Ошибка выполнения скрипта: " + e.getMessage(), null);
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