package com.example.transfer.common;

class MethodNotAllowedException extends ApplicationException {

    MethodNotAllowedException(int code, String message) {

        super(code, message);

    }

}
