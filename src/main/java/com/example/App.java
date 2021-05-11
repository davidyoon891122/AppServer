package com.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.example.nettool.HeaderReader;
import com.example.nettool.MessageHeader;
import com.example.nettool.SocketReader;
import com.example.serviceObject.Login;
import com.example.services.LoginReader;

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
            //accept clients with thread handler
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
    


    public Handler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        DataInputStream inputStream = null;
        DataOutputStream outputStream = null;
        HeaderReader headerReader = null;
        try{
            inputStream = new DataInputStream(socket.getInputStream());
            System.out.println("inputstream avail : " +inputStream.available());
            outputStream = new DataOutputStream(socket.getOutputStream());
            while(true) {
                headerReader = new HeaderReader(inputStream);

                headerReader.run();
                
                System.out.println("Server received Data from Client");
                outputStream.writeUTF("str");

                inputStream.skip(inputStream.available());
                
                outputStream.flush();

            } 
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try{
                if(inputStream != null) inputStream.close();
                if(outputStream != null) outputStream.close();
                if(socket != null) socket.close();
            } catch(Exception ex2) {
                ex2.printStackTrace();
            }
        }
        
    }
}
