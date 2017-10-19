# Exercise 2 (Part 2): Docker Swarm

This assignment is focused on the deployment of an application on a Docker Swarm.

To save time the source code of two simple applications is already given:
* a producer application
* a consumer application

## Producer

The producer application is Payara based RESTful API which has just one endpoint to publish messages to a RabbitMQ message broker.
Because it's boring and annoying to send JSON messages to an API with Postman or cURL a Swagger UI is included (metadata endpoint: **/api/swagger**).

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
5. Create a public [GitLab](https://inf-git.fh-rosenheim.de) repository. You don't have to push anything in this repository. It's only required to be able to push containers to the internal Docker registry. (It has to be public to avoid problems with the authenication later on)
6. Check the docs of the GitLab people how to push a Docker image to the GitLab container registry
7. Build the containers (keep in mind how the image name of a container is structured: [registry/]user/repository[/optional-image-name][:tag] you might notice the structure in the `build.gradle` file of the producer application)
8. Push the containers to the internal Docker registry

## Part 2 - Deploying the application

As already mentioned the application consists of the two small applications (consumer and producer) and a RabbitMQ message broker. If you want to deploy the whole application you'll have to deploy the RabbitMQ server as well (check the Docker Hub - there might be a prebuilt image!).

To deploy an application on a Docker Swarm you have to create a `docker-compose.yml` file which describes all services and their configuration (like a normal Docker-Compose file).
Optionally you can also create networks and volumes but for now we want to keep it as simple as possible.

1. Check the [Compose reference](https://docs.docker.com/compose/compose-file/) (the swarm instance is up-to-date (Docker Engine 17.09.0) so you can use all features)
2. Login at the [Portainer UI](http://141.60.123.61:9000) (it's only available in the internal network, credentials will be given after the lecture) and explore what you can do with it (the menu entry _Stacks_ might be interesting)
3. Create a `docker-compose.yml` for your own stack and deploy it through the Portainer UI
4. When you're stack is ready open the Swagger UI and send some messages through the API. Check the logs of your consumer containers
5. That's it!