package com.example.transfer.controller.account;

import com.example.transfer.common.ApplicationExceptions;
import com.example.transfer.common.Constants;
import com.example.transfer.common.GlobalExceptionHandler;
import com.example.transfer.controller.Handler;
import com.example.transfer.controller.ResponseEntity;
import com.example.transfer.controller.StatusCode;
import com.example.transfer.model.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TransferHandler extends Handler {

    private final AccountService AccountService;

    public TransferHandler(AccountService AccountService, ObjectMapper objectMapper,
                           GlobalExceptionHandler exceptionHandler) {
        super(objectMapper, exceptionHandler);
        this.AccountService = AccountService;
    }

    @Override
    protected void execute(HttpExchange exchange) throws IOException, InterruptedException {

        byte[] response;

        if (exchange.getRequestMethod().equals("POST")) {

            final ResponseEntity entity = doPost(exchange.getRequestBody());

            exchange.getResponseHeaders().putAll(entity.getHeaders());

            exchange.sendResponseHeaders(entity.getStatusCode().getCode(), 0);

            response = super.writeResponse(entity.getBody());

        } else

            throw ApplicationExceptions.methodNotAllowed(
                    "Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI()).get();

        final OutputStream os = exchange.getResponseBody();

        os.write(response);

        os.close();

    }

    private ResponseEntity<TransferResponse> doPost(InputStream is) throws InterruptedException {

        TransferRequest transferRequest = super.readRequest(is, TransferRequest.class);

        long transactionId = AccountService.transfer(transferRequest.getIdFrom(), transferRequest.getIdTo(), transferRequest.getFundsToTransfer());

        TransferResponse response = new TransferResponse(transactionId);

        return new ResponseEntity<>(response, getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

    }

}
