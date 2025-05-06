package main.java.LoadBalancer.Examples;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Backend server running on port " + 8080);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            System.out.println("Client " + clientSocket.getRemoteSocketAddress());
            getMessageFromClient(clientSocket);
            sendMessageToClient(clientSocket);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getMessageFromClient(Socket clientSocket) throws IOException {
        InputStream inputStream = clientSocket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String message = reader.readLine();
        System.out.println("Server received: " + message);
    }

    private void sendMessageToClient(Socket clientSocket) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);
        writer.println("Hello from Server");
    }
}
