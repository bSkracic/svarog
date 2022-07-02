package hr.bskracic.svarog;

import hr.bskracic.svarog.model.SvarogMessage;
import hr.bskracic.svarog.service.DockerService;
import hr.bskracic.svarog.service.RabbitMqService;
import org.apache.xmlrpc.WebServer;

import java.io.IOException;

public class Server {

    public String create(String nodeId, String name, String image){
        System.out.printf("node_id: %s, name: %s, image: %s\n", nodeId, name, image);
        new Thread(() -> {
            String containerId = "lmao";
//            String containerId = DockerService.createContainer(nodeId, name, image);
            try {
                RabbitMqService.publishMessage(new SvarogMessage(
                        Long.valueOf(nodeId), containerId
                ));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }).start();
        return "ok";
    }

    public static void main(String[] args) {
        System.out.println("Attempting to start XML-RPC Server...");

        WebServer server = new WebServer(7070);
        server.addHandler("containerService", new Server());
        server.start();

        System.out.println("Started successfully.");
        System.out.println("Accepting requests. (Halt program to stop.)");
    }
}
