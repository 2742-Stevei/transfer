package com.example.transfer.controller.account;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.transfer.controller.Handler;

import com.example.transfer.common.Constants;
import com.example.transfer.common.ApplicationExceptions;
import com.example.transfer.common.GlobalExceptionHandler;

import com.example.transfer.controller.ResponseEntity;
import com.example.transfer.controller.StatusCode;
import com.example.transfer.model.AccountCreate;
import com.example.transfer.model.AccountService;

public class CreateHandler extends Handler {

    private final AccountService accountService;

    public CreateHandler(AccountService accountService, ObjectMapper objectMapper, GlobalExceptionHandler exceptionHandler) {

        super(objectMapper, exceptionHandler);

        this.accountService = accountService;

    }

    @Override
    protected void execute(HttpExchange exchange) throws IOException, InterruptedException {

        byte[] response;

        if (exchange.getRequestMethod().equals("PUT")) {

            ResponseEntity e = doPut(exchange.getRequestBody());

            exchange.getResponseHeaders().putAll(e.getHeaders());

            exchange.sendResponseHeaders(e.getStatusCode().getCode(), 0);

            response = super.writeResponse(e.getBody());

        } else

            throw ApplicationExceptions.methodNotAllowed(
                    "Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI()).get();

        final OutputStream os = exchange.getResponseBody();

        os.write(response);

        os.close();

    }

    private ResponseEntity<CreateResponse> doPut(InputStream is) throws InterruptedException {

        final CreateRequest createRequest = super.readRequest(is, CreateRequest.class);

        final AccountCreate accountCreate = new AccountCreate(createRequest.getName(), createRequest.getFunds());

        return new ResponseEntity<>(new CreateResponse(accountService.create(accountCreate)), getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.CREATED);

    }

}
