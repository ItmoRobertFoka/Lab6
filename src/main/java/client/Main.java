package client;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;

        System.out.println("КЛИЕНТ ПРИЛОЖЕНИЯ MOVIES");

        InputManager inputManager = new InputManager(new ConsoleSource());
        MovieMaker movieMaker = new MovieMaker(inputManager);
        UdpClient udpClient = new UdpClient(host, port);
        CommandInvoker commandInvoker = new CommandInvoker(inputManager, movieMaker, udpClient);

        System.out.println("[СИСТЕМА] Клиент запущен. Требуется авторизация.");

        boolean isAuthenticated = false;
        Scanner authScanner = new Scanner(System.in);

        while (!isAuthenticated) {
            System.out.println("Меню авторизации");
            System.out.println("1. Войти");
            System.out.println("2. Зарегистрироваться");
            System.out.println("exit. Выйти из программы");
            System.out.print("Выберите действие: ");

            String choice = authScanner.nextLine().trim().toLowerCase();

            if (choice.equals("exit")) {
                System.out.println("Завершение работы клиента.");
                System.exit(0);
            }

            if (!choice.equals("1") && !choice.equals("2") && !choice.equals("login") && !choice.equals("register")) {
                System.out.println("Ошибка: Пожалуйста, выберите 1, 2 или exit.");
                continue;
            }

            System.out.print("Введите логин: ");
            String login = authScanner.nextLine().trim();
            System.out.print("Введите пароль: ");
            String password = authScanner.nextLine().trim();

            if (login.isEmpty() || password.isEmpty()) {
                System.out.println("Ошибка: Логин и пароль не могут быть пустыми.");
                continue;
            }

            String authCommand = (choice.equals("1") || choice.equals("login")) ? "login" : "register";

            commandInvoker.setUserCredentials(login, password);

            String responseMessage = commandInvoker.executeCommand(authCommand);

            if (responseMessage.toLowerCase().contains("успешн") || responseMessage.toLowerCase().contains("добро пожаловать")) {
                System.out.println("\n[УСПЕХ] " + responseMessage);
                isAuthenticated = true;
            } else {
                System.err.println("\n[ОТВЕТ СЕРВЕРА] " + responseMessage);
                commandInvoker.setUserCredentials("default_user", "default_password");
            }
        }


        System.out.println("\n[СИСТЕМА] Доступ разрешен. Теперь вы можете вводить команды.");

        while (true) {
            if (!inputManager.isScriptMode()) {
                System.out.print("\nВведите команду: ");
            }
            String line = inputManager.nextLine();
            if (line == null) {
                continue;
            }
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            String result = commandInvoker.executeCommand(line);
            if (!result.isEmpty()) {
                if (result.startsWith("Ошибка")) {
                    System.err.println(result);
                } else {
                    System.out.println(result);
                }
            }
        }
    }
}