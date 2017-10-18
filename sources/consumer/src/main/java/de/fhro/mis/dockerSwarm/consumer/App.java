package de.fhro.mis.dockerSwarm.consumer;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {

    public static void main(String[] args) {
        String rabbitHost = System.getenv("RABBITMQ_HOST");
        if(rabbitHost == null) rabbitHost = "localhost";
	    try (PrintingConsumer printingConsumer = new PrintingConsumer(rabbitHost)){
		    printingConsumer.registerConsumer();
		    System.out.println("Waiting for messages...");
		    while (true){
		    	System.in.read();
		    }
	    } catch (Exception e) {
		    e.printStackTrace();
	    }
    }
}
