package de.fhro.mis.dockerSwarm.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeoutException;

/**
 * @author Peter Kurfer
 * Created on 10/18/17.
 */
public class PrintingConsumer implements AutoCloseable{

	private final Connection connection;
	private final Channel channel;
	private final String queueName = "MIS";

	public PrintingConsumer(String rabbitmqHost) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(rabbitmqHost);
		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.queueDeclare(queueName, false, false, true, null);
	}

	public void registerConsumer() throws IOException {
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(String.format("Got message %s on node %s", message, InetAddress.getLocalHost().getHostName()));
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}

	@Override
	public void close() throws Exception {
		if(channel != null) {
			channel.close();
		}
		if(connection != null){
			connection.close();
		}
	}
}
