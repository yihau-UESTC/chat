package com.uestc.yihau.chat.session;

public interface Session {
    Object getAttachment();

    void setAttachment(Object attachment);

    void removeAttachment();

    void write(Object message);

    boolean isConnected();

    void close();

    boolean isWriteable();
}
