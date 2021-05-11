package com.example.nettool;

import java.io.InputStream;

import com.example.serviceObject.Login;
import com.example.services.LoginReader;

public class HeaderReader extends Reader{
    private MessageHeader header;

    public HeaderReader(InputStream inputStream) {
        super(inputStream);
        header = new MessageHeader();
    }

    public void run() {
        if(readHeader()){
            readBody();
        }else{
            return;
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
            if (serviceName.equals("001001")) {
                System.out.println("Execute Login Body Reader");
                LoginReader loginReader = new LoginReader(inputStream);
                loginReader.readBody();
                Login login = loginReader.get_Login();
                System.out.println(login.toString());

            }else {
                System.out.println("Error occurded");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public MessageHeader get_Header() {
        return header;
    }


    public void clearInputStream() {
        try {
            inputStream.skip(inputStream.available());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
