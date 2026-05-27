package client;

import common.Request;
import common.Response;
import common.SerializationUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * Сетевой клиент, работающий через неблокирующий DatagramChannel и Selector.
 */
public class UdpClient {
    private final String host;
    private final int port;
    private final int BUFFER_SIZE = 65535;
    private final int TIMEOUT = 3000;

    public UdpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Response sendAndReceive(Request request) {
        try (DatagramChannel channel = DatagramChannel.open();
             Selector selector = Selector.open()) {
            channel.configureBlocking(false);
            InetSocketAddress serverAddress = new InetSocketAddress(host, port);

            byte[] bytesToSend = SerializationUtils.serialize(request);
            ByteBuffer writeBuffer = ByteBuffer.wrap(bytesToSend);

            channel.send(writeBuffer, serverAddress);
            channel.register(selector, SelectionKey.OP_READ);
            int readyChannels = selector.select(TIMEOUT);

            if (readyChannels == 0) {
                return new Response(false, "Ошибка: Сервер временно недоступен. Попробуйте позже.", null);
            }

            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();

                if (key.isReadable()) {
                    ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
                    SocketAddress from = channel.receive(readBuffer);

                    if (from != null) {
                        readBuffer.flip();
                        byte[] receivedBytes = new byte[readBuffer.remaining()];
                        readBuffer.get(receivedBytes);

                        return (Response) SerializationUtils.deserialize(receivedBytes);
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            return new Response(false, "Ошибка сети на стороне клиента: " + e.getMessage(), null);
        }

        return new Response(false, "Неизвестная ошибка при обмене данными.", null);
    }
}