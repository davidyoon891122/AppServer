package com.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class App 
{
    final static int port = 13302;
    public static void main( String[] args )
    {
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);
            while(true) {
                try{
                    Socket socket = serverSocket.accept();
                    Handler handler = new Handler(socket);
                    handler.start();
                } catch ( Exception ex) {
                    ex.printStackTrace();
                } 
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                if ( serverSocket != null) serverSocket.close();
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
    }
}

class Handler extends Thread {
    Socket socket = null;
    DataInputStream inputStream = null;
    DataOutputStream outputStream = null;


    public Handler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        while(true) {
            try{

                inputStream = new DataInputStream(socket.getInputStream());
                outputStream = new DataOutputStream(socket.getOutputStream());

                String receivedString = inputStream.readUTF();
                System.out.println("received String : " + receivedString);

                outputStream.writeUTF(receivedString);
                System.out.println("sent received message to client.");
            } catch (Exception ex) {
                ex.printStackTrace();
                try{
                    if(inputStream != null) inputStream.close();
                    if(outputStream != null) outputStream.close();
                    if(socket != null) socket.close();
                    break;
                } catch(Exception ex2) {
                    ex2.printStackTrace();
                }
            }
        }
    }
}
