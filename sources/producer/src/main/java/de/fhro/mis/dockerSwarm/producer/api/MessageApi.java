package de.fhro.mis.dockerSwarm.producer.api;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import de.fhro.mis.dockerSwarm.producer.models.MessageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
@Api("Message API")
@Produces(MediaType.APPLICATION_JSON)
public class MessageApi {

	@Inject
	private Connection connection;
	private Channel channel;

	@POST
	@Path("/")
	@ApiOperation(value = "Send a message to a consumer", httpMethod = "POST")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Sent message successfully to RabbitMQ server"),
			@ApiResponse(code = 500, message = "Failed to send message to RabbitMQ server")
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
