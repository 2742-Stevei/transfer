package com.example.transfer.common;

class ApplicationException extends RuntimeException {

    private final int code;

    ApplicationException(int code, String message) {
        super(message);
        this.code = code;
    }

    int getCode() {

        return code;

    }

}
