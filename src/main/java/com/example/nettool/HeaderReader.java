package com.example.nettool;

import java.io.DataInputStream;
import java.io.InputStream;

import com.example.serviceObject.Login;
import com.example.serviceObject.Message;
import com.example.services.LoginReader;
import com.example.services.MessageReader;

public class HeaderReader extends Reader{
    private MessageHeader header;
    private final String userID = "qqpo12";
    private final String userPWD = "1234";
    private byte[] replyBytes = null;

    public HeaderReader(InputStream inputStream) {
        super(inputStream);
        header = new MessageHeader();
    }

    public void run() {
        try{
            System.out.println("before read header: " + inputStream.available());
            if(readHeader()){
                System.out.println("after read header: " + inputStream.available());
                readBody();
                System.out.println("after read body: " + inputStream.available());
                clearInputStream();
            }else{
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    
    public boolean readHeader() {
        try{
            header.set_length(readInt());
            header.set_process(readShort());
            header.set_service(readShort());
            header.set_window(readInt());
            header.set_control(readByte());
            header.set_flag(readByte());
            readShort();
        } catch (Exception ex) {
            ex.printStackTrace();
            header.set_length(0);
            header.set_process((short)0);
            header.set_service((short)0);
            header.set_window(0);
            header.set_control((byte)0);
            header.set_flag((byte)0);
            return false;
        }
        return true;
    }


    public void readBody() {
        try {
            String serviceName = header.makeService();
            System.out.println(serviceName);
            //check services -> how to make clean codes on this part.
            if (serviceName.equals("001001")) {
                System.out.println("System Alert: Execute Login Body Reader");
                LoginReader loginReader = new LoginReader(inputStream);
                loginReader.readBody();
                Login login = loginReader.get_Login();


                MessageQuery messageQuery = new MessageQuery();
                if(login.get_userID().equals(userID)) {
                    System.out.println("System Alert: server is checking");
                    if(login.get_userPWD().equals(userPWD)){
                        System.out.println("System Alert: server has checked USER IDENTITY");
                        messageQuery.set_result((byte)1);
                        messageQuery.set_message("server has checked USER IDENTITY");
                    }else {
                        System.out.println("System Alert: Wrong Password");
                        messageQuery.set_result((byte)0);
                        messageQuery.set_message("Please check your password!");
                    }
                } else {
                    System.out.println("System Alert: Not exist User ID");
                    messageQuery.set_result((byte)0);
                    messageQuery.set_message("Please check your ID");
                }
                System.out.println(login.toString());
                replyBytes = messageQuery.getBytes();

            }else if (serviceName.equals("001002")) {
                System.out.println("System Alert: Execute Message Body Reader");
                MessageReader messageReader = new MessageReader(inputStream);
                messageReader.readBody();
                Message message = messageReader.get_message();
                
                System.out.println(message.get_result() + message.get_message());
                if (message.get_result() == (byte)1) {
                    System.out.println("message from counter part : " +message.toString());
                }
                System.out.println("message from counter part : " +message.toString());
                
            }else {
                System.out.printf("Not exist service : %s\n", serviceName );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public byte[] get_replyBytes() {
        return this.replyBytes;
    }

    public MessageHeader get_Header() {
        return header;
    }





}
