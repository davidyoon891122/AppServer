package com.example.nettool;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class NetManagerResult {
    public static final int MaxListLength = 100;
    public static final int HeaderLength = MessageHeader.HeaderLength;
    
    private int length;
    private int dataLength;
    private short process;
    private short service;
    private int window;
    private byte control;
    private byte flag;

    private byte serial;
    private byte subfunction;
    private boolean isError;
    private boolean isWarning;
    private short errorCode;
    private String message;

    private DataInputStream inputStream;

    public NetManagerResult(InputStream stream, boolean useDataHeader) {
        inputStream = new DataInputStream(stream);

        try {
            readHeader();
            if(useDataHeader) {
                readDataHeader();
                if(isError)
                    return;
            } else {
                isError = false;
            }

            readData();

            readStates();
        } catch (Exception ex) {
            setError((byte) -1, "Error occured while communicate with the server(NMROO). Please try again." + ex.toString());
        }

    }


    public NetManagerResult(InputStream stream) {
        this(stream, true);
    }

    private void read(byte[] buffer) throws IOException {
        inputStream.readFully(buffer);
    }

    private void readHeader() {
        try {
            length = readInt();
            process = readShort();
            service = readShort();
            window = readInt();
            control = readByte();
            flag = readByte();
            readShort();
        } catch (Exception ex) {
            length = 0;
            process = 0;
            service = 0;
            window = 0;
            control = 0;
            flag = 0;
            setError((byte)-1, "Error occured while communicate with the server(NMR01). Please try again." + ex.toString());
        }
    }

    private void readDataHeader() throws Exception {
        isError = true;
        isWarning = true;
        errorCode = -1;
        message = "";

        serial = readByte();
        subfunction = readByte();

        byte errorFlag = readByte();

        if(errorFlag != (byte) 0) {
            isError = true;
            errorCode = readShort();
            message = readString();
            clearInputStream();
            return;
        }

        isError = false;

        byte warningFlag = readByte();

        if(warningFlag != (byte) 0) {
            isWarning = true;
            errorCode = readShort();
            message = readString();
            return;
        }

        isWarning = false;
        errorCode = 0;
    }

    protected void readData() throws Exception {

    }

    protected void readStates() throws Exception {

    }

    protected void setError(short errorCode, String message) {
        isError = true;
        this.errorCode = errorCode;
        this.message = message;
        clearInputStream();
    }

    protected void clearInputStream() {
        try {
            inputStream.skip(inputStream.available());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean get_isError() {
        return isError;
    }

    public boolean get_isWarning() {
        return isWarning;
    }

    public short get_errorCode() {
        return errorCode;
    }

    public String get_message() {
        return message;
    }

    public byte get_serial() {
        return serial;
    }

    public byte get_subfunction() {
        return subfunction;
    }

    public int get_length() {
        return length;
    }

    public short get_process() {
        return process;
    }

    public short get_service() {
        return service;
    }

    public int get_window() {
        return window;
    }

    public byte get_control() {
        return control;
    }

    public byte get_flag() {
        return flag;
    }

    
    //2021.05.05

    private int readInt() throws IOException{
        byte[] bytes = new byte[4];
        read(bytes);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        int result = buffer.getInt();

        return result;
    }


    private short readShort() throws IOException {
        byte[] bytes = new byte[2];
        read(bytes);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        short result = buffer.getShort();

        return result;
    }

    
    private byte readByte() throws IOException{
        byte[] bytes = new byte[1];
        read(bytes);
        return bytes[0];
    }


    private String readString() throws IOException{
        short length = readShort();
        byte[] bytes = new byte[length];
        read(bytes);
        String result = bytes.toString();
        return result;
    }




}
