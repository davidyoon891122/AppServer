package com.example.nettool;

import java.nio.ByteBuffer;

import com.example.types.TTypes.VarLengthString;

public class NetManagerQuery {
    protected byte[] headerBytes;
    protected MessageHeader header;

    protected byte[] dataBytes;
    protected ByteBuffer dataBuffer;

    protected static final int DefaultDataBufferSize = 512;

    private int process;

    public NetManagerQuery() {
        header = new MessageHeader();
        allocateData(DefaultDataBufferSize);
    }

    protected void allocateData(int nLength) {
        dataBytes = new byte[nLength];
        dataBuffer = ByteBuffer.wrap(dataBytes);
    }

    public int get_length() {
        return headerLength() + dataLength();
    }

    static public int headerLength() {
        return MessageHeader.HeaderLength;
    }

    public int dataLength() {
        return dataBuffer.remaining();
    }

    protected void set_process(int value) {
        process = value;
        header.set_process((short)process);
    }

    protected void set_service(int value) {
        header.set_service((short)value);
    }

    protected void willBuildDataBytes() {

    }

    protected void putByteData(byte value) {
        dataBuffer.put(value);
    }

    protected void putIntData(int value) {
        dataBuffer.putInt(value);
    }

    protected void putShortData(short value) {
        dataBuffer.putShort(value);
    }

    protected void putDoubleData(double value) {
        dataBuffer.putDouble(value);
    }

    protected void putStringData(String value) {
        dataBuffer.put(new VarLengthString(value).getBytes());
    }

    public void buildDataBytes() {
        dataBuffer.clear();
        willBuildDataBytes();
        dataBuffer.flip();
    }


    public byte[] getBytes() {
        buildDataBytes();
        int length = get_length();
        System.out.println(length);
        header.set_length(length);
        header.set_window(0);
        header.set_control((byte)0);
        header.set_flag((byte)0);
        headerBytes = header.getBytes();
        byte[] resultBytes = new byte[length];


        System.arraycopy(headerBytes, 0, resultBytes, 0, headerLength());
        System.arraycopy(dataBytes, 0, resultBytes, headerLength(), dataLength());

        return resultBytes;
    }

}
