package com.example.serviceObject;

public class Message {
    private byte result;
    private String message;

    public Message() {
    }

    public void set_message(String message) {
        this.message = message;
    }

    public void set_result(byte result) {
        this.result = result;
    }

    public String get_message() {
        return this.message;
    }

    public byte get_result() {
        return this.result;
    }

    @Override
    public String toString() {
        return "Message [message=" + message + "]";
    }

    

}
