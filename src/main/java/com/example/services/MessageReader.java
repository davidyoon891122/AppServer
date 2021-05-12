package com.example.services;

import java.io.InputStream;

import com.example.nettool.Reader;
import com.example.serviceObject.Message;

public class MessageReader extends Reader {
    Message message;

    public MessageReader(InputStream inputStream) {
        super(inputStream);
        this.message = new Message();
    }

    public void readBody() {
        try {
            this.message.set_result(readByte());
            this.message.set_message(readString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public Message get_message() {
        return this.message;
    }

}
