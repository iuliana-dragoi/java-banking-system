package main.java.LoadBalancer;

import main.java.LoadBalancer.Clients.RequestType;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SimpleLoadBalancer {

    private static final Map<RequestType, InetSocketAddress> routingTable = new HashMap<>();

    static {
        routingTable.put(RequestType.CHECK_BALANCE, new InetSocketAddress("localhost", 8081));
        routingTable.put(RequestType.DEPOSIT, new InetSocketAddress("localhost", 8082));
    }

    public static void main(String[] args) throws IOException {
        new SimpleLoadBalancer().start();
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Load balancer running on port 8080");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    private void handleClient(Socket clientSocket) {

        try {
            // Get message from Client
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            RequestType requestType = (RequestType) inputStream.readObject();
            System.out.println("Load Balancer received: " + requestType);

            // Select server by request type
            InetSocketAddress targetServer = routingTable.get(requestType);

            // Send to selected server
            String response = sendToBackend(targetServer.getHostName(), targetServer.getPort(), requestType.name());

            // Send message to Client
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            outputStream.writeObject(response);
            outputStream.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String sendToBackend(String backendServer, int port, String message) throws IOException, ClassNotFoundException {
        Socket backendSocket = new Socket(backendServer, port);
        ObjectOutputStream outputStream = new ObjectOutputStream(backendSocket.getOutputStream());
        outputStream.writeObject(message);
        outputStream.flush();

        ObjectInputStream inputStream = new ObjectInputStream(backendSocket.getInputStream());
        return (String) inputStream.readObject();
    }


}
