package com.example.nettool;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.example.services.LoginServiceQuery;
import com.example.services.ServiceDictionary;
import com.example.services.ServiceInterface;

public class SocketReader {
    private static final String TAG = "SocketReader";
    private DataInputStream inputStream = null;

    private byte[] headerByte = null;
    private MessageHeader header = null;
    
    private int headerLength = MessageHeader.HeaderLength;
    private static final String encodingType = "UTF-8";

    public SocketReader() {

    }

    public SocketReader(InputStream inputStream) {
        this.inputStream = new DataInputStream(inputStream);
        header = new MessageHeader();
    }


    public void readService() {
        try{
            readHeader();
            ServiceInterface serviceInterface = getService(header.process, header.service);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void readHeader() {
        try{
            header.length = readInt();
            header.process = readShort();
            header.service = readShort();
            header.window = readInt();
            header.control = readByte();
            header.flag = readByte();
            readShort();
            System.out.println(header.toString());
        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }
    

    private void read(byte[] bytes) throws IOException{
        inputStream.readFully(bytes);
    }



    protected int readInt() throws Exception{
        byte[] bytes = new byte[4];
        read(bytes);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        int result = buffer.getInt();
        return result;
    }

    protected short readShort() throws Exception {
        byte[] bytes = new byte[2];
        read(bytes);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        short result = buffer.getShort();
        return result;
    }

    protected double readDouble() throws Exception {
        byte[] bytes = new byte[8];
        read(bytes);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        double result = buffer.getDouble();
        return result;
    }

    protected byte readByte() throws Exception {
        byte[] bytes = new byte[1];
        read(bytes);
        return bytes[0];
    }


    protected float readFloat() throws Exception {
        byte[] bytes = new byte[4];
        read(bytes);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        float result = buffer.getFloat();
        return result;

    }

    protected String readString() throws Exception {
        short length = readShort();
        byte[] bytes = new byte[length];
        read(bytes);
        String result = new String(bytes, encodingType);
        return result;
    }



    private ServiceInterface getService(short process, short service) {
        String serviceName = String.format("%03d",process) + String.format("%03d",service);
        System.out.println("serviceName : " + serviceName);
        String serviceValue = (String) ServiceDictionary.serviceMap.get(serviceName);

        System.out.println("serviceValue : " + serviceValue);
        ServiceInterface serviceInterface = null;

        if (serviceValue == "Login"){
            //serviceInterface = new LoginServiceQuery(this.inputStream);
            serviceInterface.readBody();
            
        }else {

        }


        return serviceInterface;
    }




}
