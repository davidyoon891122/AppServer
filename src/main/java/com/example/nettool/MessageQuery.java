package com.example.nettool;

public class MessageQuery extends NetManagerQuery{
    private String message;
    private byte result;

    public MessageQuery() {
        set_process(001);
        set_service(002);
    }

    public void set_message(String message) {
        this.message = message;
    }

    public void set_result(byte result) {
        this.result = result;
    }


    @Override
    protected void willBuildDataBytes() {
        putByteData(this.result);
        putStringData(this.message);
    }
}
