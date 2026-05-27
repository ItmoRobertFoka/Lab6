package server;

import common.Request;
import common.Response;
import common.SerializationUtils;
import server.commands.Command;
import server.managers.CollectionManager;
import server.managers.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Сетевой сервер, работающий по протоколу UDP.
 * Консоль и сеть разделены максимально просто и безопасно.
 */
public class UdpServer {
    private static final Logger logger = LoggerFactory.getLogger(UdpServer.class);
    private final int port;
    private final CommandManager commandManager;
    private final CollectionManager collectionManager;
    private DatagramSocket socket;
    private final int BUFFER_SIZE = 65535;

    public UdpServer(int port, CommandManager commandManager, CollectionManager collectionManager) {
        this.port = port;
        this.commandManager = commandManager;
        this.collectionManager = collectionManager;
    }

    public void start() {
        try {
            socket = new DatagramSocket(port);
            logger.info("UDP-сервер успешно запущен и ожидает подключений на порту {}", port);

            // -----------------------------------------------------------------
            // КРОШЕЧНАЯ СЛУЖЕБНАЯЧИТАЛКА КОНСОЛИ (чтобы сеть никогда не зависала)
            // -----------------------------------------------------------------
            Thread consoleThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        line = line.trim().toLowerCase();
                        if (line.equals("save")) {
                            String status = collectionManager.save();
                            logger.info("[КОНСОЛЬ СЕРВЕРА] {}", status);
                        } else if (line.equals("exit")) {
                            logger.info("[КОНСОЛЬ СЕРВЕРА] Получена команда exit. Сохраняем коллекцию...");
                            collectionManager.save();
                            logger.info("[КОНСОЛЬ СЕРВЕРА] Сервер остановлен.");
                            System.exit(0);
                        } else if (!line.isEmpty()) {
                            logger.warn("[КОНСОЛЬ СЕРВЕРА] Неизвестная команда '{}'. Доступно: save, exit", line);
                        }
                    }
                } catch (IOException e) {
                    logger.error("Ошибка при чтении консоли сервера: ", e);
                }
            });
            consoleThread.setDaemon(true); // Завершится сам при закрытии сервера
            consoleThread.start();
            // -----------------------------------------------------------------

            byte[] buffer = new byte[BUFFER_SIZE];

            // Твой родной, чистый сетевой цикл. Никаких таймаутов, работает железно!
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivePacket); // Спокойно ждет пакет, не нагружая процессор

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                Response response;

                try {
                    byte[] receivedData = new byte[receivePacket.getLength()];
                    System.arraycopy(receivePacket.getData(), 0, receivedData, 0, receivePacket.getLength());

                    Request request = (Request) SerializationUtils.deserialize(receivedData);
                    logger.info("Получен новый запрос от клиента [{}:{}]", clientAddress, clientPort);
                    logger.info("Выполнение команды: '{}'", request.getCommandName());

                    String commandName = request.getCommandName().toLowerCase();
                    Command command = commandManager.getCommandMap().get(commandName);

                    if (command != null) {
                        response = command.execute(request);
                    } else {
                        response = new Response(false, "Ошибка: Команда '" + commandName + "' не найдена на сервере.", null);
                    }

                } catch (ClassNotFoundException e) {
                    response = new Response(false, "Ошибка сервера: не найден класс запроса при десериализации.", null);
                } catch (IOException e) {
                    response = new Response(false, "Ошибка сервера при чтении данных: " + e.getMessage(), null);
                } catch (Exception e) {
                    response = new Response(false, "Критическая ошибка сервера: " + e.getMessage(), null);
                }

                sendResponse(response, clientAddress, clientPort);
            }

        } catch (SocketException e) {
            System.err.println("[СЕРВЕР] Ошибка инициализации сокета: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("[СЕРВЕР] Ошибка ввода-вывода : " + e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

    private void sendResponse(Response response, InetAddress address, int port) {
        try {
            byte[] sendData = SerializationUtils.serialize(response);
            if (sendData.length > BUFFER_SIZE) {
                System.err.println("[СЕРВЕР] Ошибка: Ответ слишком большой для отправки по UDP");
                Response errorResponse = new Response(false, "Ошибка: Ответ сервера превысил размер пакета UDP", null);
                sendData = SerializationUtils.serialize(errorResponse);
            }

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
            socket.send(sendPacket);

        } catch (IOException e) {
            logger.error("Критическая ошибка в работе сервера: ", e);
        }
    }
}