# Exercise 2 (Part 2): Docker Swarm

This assignment is focused on the usage of `docker-compose`.

To save time the source code of two simple applications is already given:

* a producer application
* a consumer application

## Producer

The producer application is Payara based RESTful API which has just one endpoint to publish messages to a RabbitMQ message broker.
Because it's boring and annoying to send JSON messages to an API with Postman or cURL a Swagger UI is included (metadata endpoint: **/openapi/**).

The producer just takes the body of the JSON object and sends it to the RabbitMQ server (fire and forget).

_We'll talk about message driven applications/services at a later time._

## Consumer

The consumer application is a plain old Java command-line application. It registers itself at the RabbitMQ server and waits for messages to be delivered.
Whenever a message reaches the consumer it writes the message body to the STDOUT (including the hostname to be able to identify who received the message later on).

## Assignment part 1 - Preparing the container images

1. Clone the repository
2. Import both applications to your IntelliJ (It doesn't matter which one you import first. Add the second one by clicking _File -> New -> Module from existing source_)
3. Check the given Dockerfiles - they're really simple but nothing more is required to get the containers up and running
4. Check how you can build the containers (_Hint: the producer has a Docker plugin applied in the `gradle.build` file, the consumer has a `dockerBuild.sh` script_). You might have to adopt the files depending on your operating system.

## Part 2 - Deploying the application

As already mentioned the application consists of the two small applications (consumer and producer) and a RabbitMQ message broker. If you want to deploy the whole application you'll have to deploy the RabbitMQ server as well (check the Docker Hub - there might be a prebuilt image!).

You should start by writing a very simple `docker-compose.yml` that is already able to start the whole application stack.
As soon as you're done with that you can extend the `docker-compose.yml` to isolate the producer and consumers in different networks (keep in mind that both have to be able to reach the RabbitMQ server!).

1. Check the [Compose reference](https://docs.docker.com/compose/compose-file/)
2. Create a simple `docker-compose.yml`
3. Start the application stack
4. Open the Swagger UI and try to get the required meta data. You'll notice that the Swagger UI will perfectly display the APIs but won't execute calls successfully because it assumes for some reason `HTTPS` as scheme. Copy the `cURL` commands, adapt them for your environment and try to call the API from CLI. Alternatively you can also the built-in REST client in your IntelliJ Idea.
5. Scale the `consumer` part to see how the messages are distributed across your consumers and how fast a new container is created