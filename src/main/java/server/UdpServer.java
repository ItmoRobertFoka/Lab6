package server;

import common.Request;
import common.Response;
import common.SerializationUtils;
import server.commands.Command;
import server.managers.CollectionManager;
import server.managers.CommandManager;
import server.managers.MovieDatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * Сетевой сервер, работающий по протоколу UDP с многопоточной обработкой.
 */
public class UdpServer {
    private static final Logger logger = LoggerFactory.getLogger(UdpServer.class);
    private final int port;
    private final CommandManager commandManager;
    private final CollectionManager collectionManager;
    private final MovieDatabaseManager movieDbManager;
    private DatagramSocket socket;
    private final int BUFFER_SIZE = 65535;

    private final ExecutorService readerPool = Executors.newCachedThreadPool();
    private final ForkJoinPool processorPool = new ForkJoinPool();
    private final ExecutorService senderPool = Executors.newCachedThreadPool();

    public UdpServer(int port, CommandManager commandManager, CollectionManager collectionManager, MovieDatabaseManager movieDbManager) {
        this.port = port;
        this.commandManager = commandManager;
        this.collectionManager = collectionManager;
        this.movieDbManager = movieDbManager;
    }

    public void start() {
        try {
            socket = new DatagramSocket(port);
            logger.info("UDP-сервер успешно запущен и ожидает подключений на порту {}", port);

            Thread consoleThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        line = line.trim().toLowerCase();
                        if (line.equals("save")) {
                            logger.info("[КОНСОЛЬ СЕРВЕРА] В Лабораторной 7 сохранение автоматическое. Все изменения сразу записываются в PostgreSQL.");
                        } else if (line.equals("exit")) {
                            logger.info("[КОНСОЛЬ СЕРВЕРА] Завершение работы сервера.");
                            System.exit(0);
                        } else if (!line.isEmpty()) {
                            logger.warn("[КОНСОЛЬ СЕРВЕРА] Неизвестная команда '{}'. Доступно: save, exit", line);
                        }
                    }
                } catch (IOException e) {
                    logger.error("Ошибка при чтении консоли сервера: ", e);
                }
            });
            consoleThread.setDaemon(true);
            consoleThread.start();

            while (true) {
                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivePacket);

                final InetAddress clientAddress = receivePacket.getAddress();
                final int clientPort = receivePacket.getPort();
                final int packetLength = receivePacket.getLength();
                final byte[] packetData = receivePacket.getData();

                readerPool.submit(() -> {
                    try {
                        byte[] receivedData = new byte[packetLength];
                        System.arraycopy(packetData, 0, receivedData, 0, packetLength);

                        Request request = (Request) SerializationUtils.deserialize(receivedData);
                        logger.info("Получен новый запрос от клиента [{}:{}]", clientAddress, clientPort);
                        logger.info("Выполнение команды: '{}'", request.getCommandName());

                        processorPool.submit(() -> {
                            Response response;
                            try {
                                String commandName = request.getCommandName().toLowerCase();
                                String userLogin = request.getLogin();
                                String userPassword = request.getPassword();

                                if (commandName.equals("login")) {
                                    boolean success = movieDbManager.checkUserCredentials(userLogin, userPassword);
                                    if (success) {
                                        response = new Response(true, "Успешный вход! Добро пожаловать, " + userLogin, null);
                                    } else {
                                        response = new Response(false, "Ошибка: Неверный логин или пароль.", null);
                                    }
                                }
                                else if (commandName.equals("register")) {
                                    boolean success = movieDbManager.registerUser(userLogin, userPassword);
                                    if (success) {
                                        response = new Response(true, "Пользователь успешно зарегистрирован! Вход выполнен.", null);
                                    } else {
                                        response = new Response(false, "Ошибка: Пользователь с таким логином уже существует.", null);
                                    }
                                }
                                else {
                                    boolean isUserValid = movieDbManager.checkUserCredentials(userLogin, userPassword);

                                    if (!isUserValid) {
                                        response = new Response(false, "Ошибка: Сессия не авторизована или пароль изменен. Перезапустите клиент.", null);
                                    } else {
                                        Command command = commandManager.getCommandMap().get(commandName);
                                        if (command != null) {
                                            response = command.execute(request);
                                        } else {
                                            response = new Response(false, "Ошибка: Команда '" + commandName + "' не найдена на сервере.", null);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                response = new Response(false, "Ошибка обработки запроса: " + e.getMessage(), null);
                            }


                            final Response finalResponse = response;

                            senderPool.submit(() -> {
                                sendResponse(finalResponse, clientAddress, clientPort);
                            });
                        });

                    } catch (ClassNotFoundException e) {
                        Response errResp = new Response(false, "Ошибка сервера: не найден класс запроса при десериализации.", null);
                        senderPool.submit(() -> sendResponse(errResp, clientAddress, clientPort));
                    } catch (IOException e) {
                        Response errResp = new Response(false, "Ошибка сервера при чтении данных: " + e.getMessage(), null);
                        senderPool.submit(() -> sendResponse(errResp, clientAddress, clientPort));
                    } catch (Exception e) {
                        Response errResp = new Response(false, "Критическая ошибка сервера: " + e.getMessage(), null);
                        senderPool.submit(() -> sendResponse(errResp, clientAddress, clientPort));
                    }
                });
            }

        } catch (SocketException e) {
            System.err.println("[СЕРВЕP] Ошибка инициализации сокета: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("[СЕРВЕP] Ошибка ввода-вывода : " + e.getMessage());
        } finally {
            readerPool.shutdown();
            processorPool.shutdown();
            senderPool.shutdown();
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
            logger.error("Критическая ошибка в работе сервера при отправке: ", e);
        }
    }
}