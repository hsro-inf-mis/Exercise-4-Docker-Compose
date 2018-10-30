package main

import (
	"fmt"
	"github.com/streadway/amqp"
	"log"
	"os"
	"strings"
)

const (
	// Environment variable key used to overwrite the hostname of the RabbitMQ server
	RabbitMQHostnameKey = "RABBITMQ_HOST"
)

func main() {

	env := loadEnv()
	env[RabbitMQHostnameKey] = "localhost"

	conn, err := amqp.Dial(fmt.Sprintf("amqp://guest:guest@%s:5672/", env[RabbitMQHostnameKey]))
	failOnError(err, "Failed to connect to RabbitMQ")

	// ensure that RabbitMQ connection is closed on exit
	defer conn.Close()

	ch, err := conn.Channel()
	failOnError(err, "Failed to open a channel")

	// ensure that channel connection is closed on exit
	defer ch.Close()

	q, err := ch.QueueDeclare(
		"MIS",
		false,
		true,
		false,
		false,
		nil)

	failOnError(err, "Failed to declare a queue")

	// get a channel to read messages delivered via RabbitMQ
	msgs, err := ch.Consume(
		q.Name,
		"",
		true,
		false,
		false,
		false,
		nil)

	failOnError(err, "Failed to register a consumer")

	forever := make(chan bool)

	go func() {
		for d := range msgs {
			log.Printf("Received a message: %s", d.Body)
		}
	}()

	log.Printf("[*] Waiting for messages. To exit press CTRL+c")
	<-forever
}

// Generic error handler to print a logging message whenever an error occurred
func failOnError(err error, msg string) {
	if err != nil {
		log.Fatalf("%s: %s", msg, err)
	}
}

// Load the whole environment to a map[string]string to avoid multiple iterations to access multiple environment variables
func loadEnv() map[string]string {
	env := make(map[string]string)

	for _, v := range os.Environ() {
		pair := strings.Split(v, "=")
		env[pair[0]] = pair[1]
	}

	return env
}
