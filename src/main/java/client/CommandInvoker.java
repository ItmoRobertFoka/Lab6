package client;

import common.Movie;
import common.Request;
import common.Response;

public class CommandInvoker {
    private final InputManager inputManager;
    private final MovieMaker movieMaker;
    private final UdpClient udpClient;

    private String login = "default_user";
    private String password = "default_password";

    public CommandInvoker(InputManager inputManager, MovieMaker movieMaker, UdpClient udpClient) {
        this.inputManager = inputManager;
        this.movieMaker = movieMaker;
        this.udpClient = udpClient;
    }

    public void setUserCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String executeCommand(String line) {
        String[] parts = line.trim().split("\\s+", 2);
        String commandName = parts[0].toLowerCase();
        String stringArg = parts.length > 1 ? parts[1].trim() : null;

        if (commandName.equals("exit")) {
            System.out.println("Завершение работы клиента");
            System.exit(0);
            return "";
        }

        if (commandName.equals("save")) {
            return "Ошибка: Команда 'save' недоступна на клиенте.";
        }

        if (commandName.equals("execute_script")) {
            if (stringArg == null || stringArg.isBlank()) return "Ошибка: Не указано имя файла.";
            try {
                inputManager.pushFile(stringArg);
                return "Запуск выполнения скрипта: " + stringArg;
            } catch (Exception e) {
                return "Ошибка при открытии скрипта: " + e.getMessage();
            }
        }

        Request request;

        if (commandName.equals("add") || commandName.equals("add_if_min") || commandName.equals("remove_greater")) {
            Movie movie = movieMaker.createMovie();
            if (movie == null) return "Не удалось создать объект Movie";
            request = new Request(commandName, movie, null, login, password);
        }

        else if (commandName.equals("update_id")) {
            if (stringArg == null || stringArg.isEmpty()) return "Ошибка: Укажите id";

            Request checkRequest = new Request(commandName, stringArg, login, password);
            Response checkResponse = udpClient.sendAndReceive(checkRequest);

            if (checkResponse == null) return "Сервер временно недоступен";

            if (!checkResponse.getStatus()) {
                return checkResponse.getMessage();
            }

            System.out.println(checkResponse.getMessage());
            Movie newMovie = movieMaker.createMovie();
            if (newMovie == null) return "Не удалось создать объект Movie";

            newMovie.setId(Integer.parseInt(stringArg));
            request = new Request(commandName, newMovie, null, login, password);
        }

        else if (commandName.equals("count_greater_than_director")) {
            common.Person director = movieMaker.inputDirector();
            request = new Request(commandName, director, null, login, password);
        }
        else {
            request = new Request(commandName, stringArg, login, password);
        }

        Response response = udpClient.sendAndReceive(request);
        if (response == null) return "Сервер временно недоступен";

        if (commandName.equals("show") && response.getStatus() && response.getMovieCollection() != null) {
            StringBuilder sb = new StringBuilder(response.getMessage() + ":\n");
            for (Object obj : response.getMovieCollection()) {
                sb.append(obj.toString()).append("\n");
            }
            return sb.toString().trim();
        }

        return response.getMessage();
    }
}