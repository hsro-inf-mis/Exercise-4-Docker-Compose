package de.fhro.mis.dockerSwarm.producer.cdi;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.ejb.Singleton;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Peter Kurfer
 * Created on 10/19/17.
 */
@Singleton
public class RabbitMqProducer {

	private final ConnectionFactory connectionFactory;

	public RabbitMqProducer() {
		String rabbitMqHost = System.getenv("RABBITMQ_HOST");
		connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(rabbitMqHost == null ? "localhost" : rabbitMqHost);
	}

	@Produces
	public Connection createConnection() throws IOException, TimeoutException {
		return connectionFactory.newConnection();
	}

	public void closeConnection(@Disposes Connection connection){
		try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
