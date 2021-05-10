package com.example.nettool;

import java.nio.ByteBuffer;

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

    
}
