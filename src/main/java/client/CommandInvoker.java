package client;

import common.Movie;
import common.Request;
import common.Response;
import java.util.List;

public class CommandInvoker {
    private final InputManager inputManager;
    private final MovieMaker movieMaker;
    private final UdpClient udpClient;

    public CommandInvoker(InputManager inputManager, MovieMaker movieMaker, UdpClient udpClient) {
        this.inputManager = inputManager;
        this.movieMaker = movieMaker;
        this.udpClient = udpClient;
    }

    public String executeCommand(String line) {
        String[] parts = line.trim().split("\\s+");
        String commandName = parts[0].toLowerCase();
        String stringArg = parts.length > 1 ? line.substring(parts[0].length()).trim() : null;

        if (commandName.equals("exit")) {
            System.out.println("Завершение работы клиента");
            System.exit(0);
            return "";
        }

        // БЛОКИРОВКА SAVE НА КЛИЕНТЕ
        if (commandName.equals("save")) {
            return "Ошибка: Команда 'save' недоступна на клиенте. Ей управляет сервер через свою консоль.";
        }

        if (commandName.equals("execute_script")) {
            if (stringArg == null || stringArg.isBlank()) {
                return "Ошибка: Не указано имя файла скрипта.";
            }
            try {
                inputManager.pushFile(stringArg);
                return "Запуск выполнения скрипта: " + stringArg;
            } catch (Exception e) {
                return "Ошибка при открытии скрипта: " + e.getMessage();
            }
        }

        Request request;

        // ИСПРАВЛЕНО: Поменяли местами аргументы (Объект, Строка)
        if (commandName.equals("add") || commandName.equals("add_if_min") || commandName.equals("remove_greater")) {
            Movie movie = movieMaker.createMovie();
            if (movie == null) {
                return "Не удалось создать объект Movie";
            }
            request = new Request(commandName, movie, null);
        }
        else if (commandName.equals("update_id")) {
            if (stringArg == null || stringArg.isEmpty()) {
                return "Ошибка: Введите корректный id фильма, который хотите обновить";
            }
            Movie newMovie = movieMaker.createMovie();
            if (newMovie == null) {
                return "Не удалось создать объект Movie для обновления";
            }
            request = new Request(commandName, newMovie, stringArg);
        }
        else if (commandName.equals("count_greater_than_director")) {
            if (!inputManager.isScriptMode()) {
                System.out.println("Ввод данных директора для сравнения");
            }
            common.Person director = movieMaker.inputDirector();
            request = new Request(commandName, director, null);
        }
        else {
            request = new Request(commandName, null, stringArg);
        }

        Response response = udpClient.sendAndReceive(request);
        if (response == null) {
            return "Сервер временно недоступен";
        }

        if (commandName.equals("show") && response.getStatus() && response.getMovieCollection() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(response.getMessage()).append(":\n");

            List<?> movies = response.getMovieCollection();
            for (Object obj : movies) {
                sb.append(obj.toString()).append("\n");
            }
            return sb.toString().trim();
        }

        return response.getMessage();
    }
}