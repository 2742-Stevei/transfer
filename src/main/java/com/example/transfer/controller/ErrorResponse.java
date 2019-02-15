package com.example.transfer.controller;

public class ErrorResponse {

    private final int code;

    private final String message;

    @java.beans.ConstructorProperties({"code", "message"})
    public ErrorResponse(int code, String message) {

        this.code = code;

        this.message = message;

    }

    public int getCode() {

        return code;

    }

    public String getMessage() {

        return message;

    }

}
