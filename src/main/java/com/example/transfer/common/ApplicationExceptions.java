package com.example.transfer.common;

import com.example.transfer.controller.StatusCode;

import java.util.function.Function;
import java.util.function.Supplier;

public class ApplicationExceptions {

    public static Function<? super Throwable, RuntimeException> badRequest() {

        return thr -> new BadRequestException(StatusCode.BAD_REQUEST.getCode(), thr.getMessage());

    }

    public static Supplier<RuntimeException> notAcceptable() {

        return () -> new NotAcceptableException(StatusCode.NOT_ACCEPTABLE.getCode(), ErrorMessages.INSUFFICIENT_FUNDS);

    }

    public static Supplier<RuntimeException> methodNotAllowed(String message) {

        return () -> new MethodNotAllowedException(StatusCode.METHOD_NOT_ALLOWED.getCode(), message);

    }

    public static Supplier<RuntimeException> notFound(String message) {

        return () -> new ResourceNotFoundException(StatusCode.NOT_FOUND.getCode(), message);

    }

    public static Supplier<RuntimeException> gatewayTimeout(String message) {

        return () -> new GatewayTimeoutException(StatusCode.GATEWAY_TIMEOUT.getCode(), message);

    }

}
