package main.java.LoadBalancer.Clients;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Client1 {

    private final static AtomicInteger counter = new AtomicInteger(0);

    private final static List<RequestType> clientRequests = Arrays.asList(
            RequestType.CHECK_BALANCE,
//            RequestType.TRANSFER,
            RequestType.DEPOSIT
//            RequestType.WITHDRAW,
//            RequestType.CHECK_BALANCE,
//            RequestType.TRANSFER,
//            RequestType.DEPOSIT,
//            RequestType.CHECK_BALANCE,
//            RequestType.CHECK_BALANCE,
//            RequestType.TRANSFER
    );

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client1 client = new Client1();

        for(int i = 0; i < clientRequests.size(); i++) {
            RequestType requestType = clientRequests.get(i);
            client.sendRequest(requestType);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendRequest(RequestType requestType) {

        int currentCounter = counter.getAndIncrement();

        try{
            InetAddress host = InetAddress.getLocalHost();
            Socket socket = new Socket(host.getHostName(), 8080);

            String response = sendToLoadBalancer(socket, requestType, currentCounter);
            System.out.println(response);
        }
        catch (IOException | RuntimeException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String sendToLoadBalancer(Socket socket, RequestType requestType, int ct) throws IOException, ClassNotFoundException {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(requestType);
        outputStream.flush();

        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        return (String) inputStream.readObject();
    }
}
