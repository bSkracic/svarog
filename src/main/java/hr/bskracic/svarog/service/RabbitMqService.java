package hr.bskracic.svarog.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import hr.bskracic.svarog.client.MessageClient;
import hr.bskracic.svarog.model.SvarogMessage;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;

public class RabbitMqService {

    private static final String EXCHANGE_NAME = "containers";
    private static final String ROUTING_KEY = "hr.bskracic.svarog";

    public static void publishMessage(SvarogMessage svarogMessage) throws IOException {
        Channel channel = MessageClient.getInstance().getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "hr.bskracic.svarog");
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, true,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                SerializationUtils.serialize(svarogMessage)
        );
    }
}
