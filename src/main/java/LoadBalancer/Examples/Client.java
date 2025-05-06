package main.java.LoadBalancer.Examples;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        new Client().run();
    }

    private void run() {

        try{
            InetAddress host = InetAddress.getLocalHost();
            System.out.println(host + " " + host.getHostAddress());
            Socket socket = new Socket(host.getHostName(), 8080);

            sendMessageToServer(socket);
            getMessageFromServer(socket);

        }
        catch (IOException | RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sendMessageToServer(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);
        writer.println("Hello from Client");
    }

    private static void getMessageFromServer(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String message = reader.readLine();
        System.out.println(message);
    }
}
