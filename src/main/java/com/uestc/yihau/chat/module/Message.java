package com.uestc.yihau.chat.module;

import com.uestc.yihau.chat.serial.Serializer;

public class Message extends Serializer{

    private String context;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    protected void read() {
        this.context = readString();
    }

    @Override
    protected void write() {
        writeString(context);
    }
}
