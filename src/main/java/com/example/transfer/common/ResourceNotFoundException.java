package com.example.transfer.common;

class ResourceNotFoundException extends ApplicationException {

    ResourceNotFoundException(int code, String message) {

        super(code, message);

    }

}
