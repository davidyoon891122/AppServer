package com.example.types.TTypes;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class VarLengthString {
    public static final String DefaultEncoding = "UTF-8";

    private ByteBuffer data;

    public VarLengthString(String value) {
        try {
            byte[] bytes = value.getBytes(DefaultEncoding);
            init(bytes);
        } catch (Exception ex) {
            ex.printStackTrace();
            init(new byte[0]);
        }
    }

    public VarLengthString(String value, String encoding) throws UnsupportedEncodingException {
        byte[] bytes = value.getBytes(encoding);
        init(bytes);

    }

    private void init(byte[] bytes) {
        data = ByteBuffer.allocate(bytes.length + 2 );
        data.putShort((short)bytes.length);
        data.put(bytes);
    }

    public int get_length() {
        return data.capacity();
    }

    public byte[] getBytes() {
        return data.array();
    }

}
