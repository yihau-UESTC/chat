package com.uestc.yihau.chat;

public class ErrorCodeException extends RuntimeException {
    private final int errorCode;


    public int getErrorCode() {
        return errorCode;
    }

    public ErrorCodeException(int errorCode){
        this.errorCode = errorCode;
    }
}
