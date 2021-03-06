package com.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    
    public static void main(String[] args) {
        String ip = "localhost";
        int port = 13302;
        InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
        int timeOut = 5000;
        String testMessage = "Test message for the server";

        Socket socket = new Socket();
        DataInputStream inputStream = null;
        DataOutputStream outputStream = null;

        try{
            socket.connect(inetSocketAddress, timeOut);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream.writeUTF(testMessage);

            System.out.println("Message is sent to server");

            String receivedMessage = inputStream.readUTF();

            System.out.printf("Message from server : %s\n ", receivedMessage);

            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try{
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
                if (socket != null) socket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }
}
