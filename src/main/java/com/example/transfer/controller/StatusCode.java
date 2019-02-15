package com.example.transfer.controller;

public enum StatusCode {

    OK(200),

    CREATED(201),

    BAD_REQUEST(400),

    NOT_FOUND(400),

    METHOD_NOT_ALLOWED(405),

    NOT_ACCEPTABLE(406),

    GATEWAY_TIMEOUT(504);

    private int code;

    StatusCode(int code) {

        this.code = code;

    }

    public int getCode() {

        return code;

    }

}
