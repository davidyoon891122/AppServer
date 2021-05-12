package com.example.nettool;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class MessageHeader {
    protected int length;
    protected short process;
    protected short service;
    protected int window;
    protected byte control;
    protected byte flag;

    protected byte[] headerBytes;
    protected ByteBuffer headerBuffer;

    final static public int HeaderLength = 16;

    public MessageHeader() {
        headerBytes = new byte[HeaderLength];
        headerBuffer = ByteBuffer.wrap(headerBytes);
    }

    public void set_length(int length) {
        this.length = length;
    }

    public void set_process(short process) {
        this.process = process;
    }

    public void set_service(short service) {
        this.service = service;
    }

    public void set_window(int window) {
        this.window = window;
    }

    public void set_control(byte control) {
        this.control = control;
    }

    public void set_flag(byte flag) {
        this.flag = flag;
    }

    public int get_length() {
        return this.length;
    }

    public short get_process() {
        return this.process;
    }

    public short get_service() {
        return this.service;
    }

    public int get_window() {
        return this.window;
    }

    public byte get_control() {
        return this.flag;
    }

    public byte get_flag() {
        return this.control;
    }

    public byte[] getBytes() {
        headerBuffer.clear();
        headerBuffer.putInt(this.length);
        headerBuffer.putShort(this.process);
        headerBuffer.putShort(this.service);
        headerBuffer.putInt(this.window);
        headerBuffer.put(this.control);
        headerBuffer.put(this.flag);
        headerBuffer.put(new byte[2]);
        headerBuffer.flip();

        return headerBytes;
    }

    public String makeService(){
        return String.format("%03d", get_process()) + String.format("%03d", get_service()); 
    }


    @Override
    public String toString() {
        return "MessageHeader [control=" + control + ", flag=" + flag + ", headerBuffer=" + headerBuffer
                + ", headerBytes=" + Arrays.toString(headerBytes) + ", length=" + length + ", process=" + process
                + ", service=" + service + ", window=" + window + "]";
    }


    
    
}
