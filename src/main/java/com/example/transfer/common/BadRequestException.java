package com.example.transfer.common;

public class BadRequestException extends ApplicationException {

    public BadRequestException(int code, String message) {

        super(code, message);

    }

}
