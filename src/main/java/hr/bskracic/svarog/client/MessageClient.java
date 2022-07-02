package hr.bskracic.svarog.client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class MessageClient {

    private static volatile MessageClient instance;
    private Channel channel;

    private MessageClient() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://localhost");
        Connection connection = factory.newConnection();
        this.channel = connection.createChannel();
    }

    public static MessageClient getInstance() {
        MessageClient result = instance;
        if (result != null) {
            return result;
        }
        synchronized (MessageClient.class) {
            if (instance == null) {
                try {
                    instance = new MessageClient();
                } catch (URISyntaxException | TimeoutException | NoSuchAlgorithmException | KeyManagementException |
                         IOException e) {
                    System.err.println(e.getMessage());
                }
            }
            return instance;
        }
    }

    public Channel getChannel() {
        return channel;
    }
}
