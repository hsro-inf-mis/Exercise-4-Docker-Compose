package de.thro.mis.containers.producer.api;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import de.thro.mis.containers.producer.models.MessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @author Peter Kurfer
 * Created on 10/18/17.
 */
@Stateless
@Path("Message")
@Produces(MediaType.APPLICATION_JSON)
public class MessageApi {

	@Inject
	private Connection connection;
	private Channel channel;

	@POST
	@Path("/")
	@Operation(summary = "Send a message to a consumer", method = "POST")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Sent message successfully to RabbitMQ server"),
			@ApiResponse(responseCode = "500", description = "Failed to send message to RabbitMQ server")
	})
	public Response sendMessage(MessageDto message) {
		try {
			channel.basicPublish("", "MIS", null, message.getMessage().getBytes());
		} catch (IOException e) {
			return Response.serverError().build();
		}
		return Response.ok().build();
	}

	@PostConstruct
	public void init() {
		try {
			channel = connection.createChannel();
			channel.queueDeclare("MIS", false, false, true, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PreDestroy
	public void closeChannel(){
		try {
			channel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
