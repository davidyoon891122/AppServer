package com.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.example.nettool.HeaderReader;
import com.example.nettool.TestQuery;

public class Client {
    
    public static void main(String[] args) {
        String ip = "10.131.158.67";
        int port = 13302;
        InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
        int timeOut = 5000;
        String testMessage = "Test message for the server";

        Socket socket = new Socket();
        DataInputStream inputStream = null;
        DataOutputStream outputStream = null;
        HeaderReader headerReader = null;

        try{
            socket.connect(inetSocketAddress, timeOut);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());

            String testId = "davidyoon";
            String testPwd = "1234";

            TestQuery testQuery = new TestQuery();
            testQuery.set_userId(testId);
            testQuery.set_userPwd(testPwd);
            byte[] resultBytes = testQuery.getBytes();



            System.out.printf("resultBytes : %s \n", resultBytes);
            outputStream.write(resultBytes);
            Thread.sleep(1000);
            System.out.println("received data Length : " + inputStream.available());
            if (inputStream.available() > 0 ) {

            
                headerReader = new HeaderReader(inputStream);

                headerReader.run();
            }

            outputStream.flush();
       

/*
            outputStream.writeUTF(testMessage);

            System.out.println("Message is sent to server");

            String receivedMessage = inputStream.readUTF();

            System.out.printf("Message from server : %s\n ", receivedMessage);
*/
            
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
