package com.example.transfer.common;

import com.example.transfer.controller.StatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

import com.example.transfer.controller.ErrorResponse;

public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;

    }

    public void handle(Throwable throwable, HttpExchange exchange) {

        try {

            throwable.printStackTrace();

            exchange.getResponseHeaders().set(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);

            ErrorResponse response = getErrorResponse(throwable, exchange);

            OutputStream responseBody = exchange.getResponseBody();

            responseBody.write(objectMapper.writeValueAsBytes(response));

            responseBody.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private ErrorResponse getErrorResponse(Throwable throwable, HttpExchange exchange) throws IOException {

        ErrorResponse errorResponse;

        if (throwable instanceof BadRequestException) {

            BadRequestException exc = (BadRequestException) throwable;

            errorResponse = new ErrorResponse(exc.getCode(), exc.getMessage());

            exchange.sendResponseHeaders(StatusCode.BAD_REQUEST.getCode(), 0);

        } else if (throwable instanceof ResourceNotFoundException) {

            ResourceNotFoundException exc = (ResourceNotFoundException) throwable;

            errorResponse = new ErrorResponse(exc.getCode(), exc.getMessage());

            exchange.sendResponseHeaders(StatusCode.NOT_FOUND.getCode(), 0);

        } else if (throwable instanceof MethodNotAllowedException) {

            MethodNotAllowedException exc = (MethodNotAllowedException) throwable;

            errorResponse = new ErrorResponse(exc.getCode(), exc.getMessage());

            exchange.sendResponseHeaders(StatusCode.METHOD_NOT_ALLOWED.getCode(), 0);

        }  else if (throwable instanceof NotAcceptableException) {

            NotAcceptableException exc = (NotAcceptableException) throwable;

            errorResponse = new ErrorResponse(exc.getCode(), exc.getMessage());

            exchange.sendResponseHeaders(StatusCode.NOT_ACCEPTABLE.getCode(), 0);

        } else {

            errorResponse = new ErrorResponse(500, throwable.getMessage());

            exchange.sendResponseHeaders(500, 0);

        }

        return errorResponse;

    }

}
