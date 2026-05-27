package client;

public class Main {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;

        System.out.println("КЛИЕНТ ПРИЛОЖЕНИЯ MOVIES");

        InputManager inputManager = new InputManager();
        MovieMaker movieMaker = new MovieMaker(inputManager);
        UdpClient udpClient = new UdpClient(host, port);
        CommandInvoker commandInvoker = new CommandInvoker(inputManager, movieMaker, udpClient);

        System.out.println("[СИСТЕМА] Клиент готов к работе. Ожидание ввода.");
        if (host.equals("localhost")) {
            System.out.println("[ИНФО] Подключение к локальному серверу на порту " + port);
        }

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