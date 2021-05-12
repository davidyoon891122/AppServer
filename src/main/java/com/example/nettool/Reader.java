package com.example.nettool;

import java.io.DataInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Reader {

    DataInputStream inputStream;
    final static String encodingType = "UTF-8";
    
    public Reader() {}

    public Reader(InputStream inputStream) {
        this.inputStream = new DataInputStream(inputStream);
    }


    private void read(byte[] bytes) throws Exception{
        this.inputStream.readFully(bytes);
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
        System.out.printf("recevied string length : %s\n", length);
        byte[] bytes = new byte[length];
        read(bytes);
        String result = new String(bytes, encodingType);
        return result;
    }


    public void clearInputStream() {
        try {
            inputStream.skip(inputStream.available());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }




    
}
