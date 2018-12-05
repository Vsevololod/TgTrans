import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class UpdaterA extends Thread {

    private final static String QUEUE_NAME = "hello";
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;

    public UpdaterA(){

        factory = new ConnectionFactory();
        factory.setHost("localhost");


        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run() {
        String message = "Hello World!";
        try {
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(" [x] Sent '" + message + "'");
    }
}
