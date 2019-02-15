package com.example.transfer.controller.account;

import com.example.transfer.common.*;
import com.example.transfer.controller.Handler;
import com.example.transfer.controller.ResponseEntity;
import com.example.transfer.controller.StatusCode;
import com.example.transfer.model.Account;
import com.example.transfer.model.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ReadHandler extends Handler {

    private final AccountService AccountService;

    public ReadHandler(AccountService AccountService, ObjectMapper objectMapper,
                       GlobalExceptionHandler exceptionHandler) {

        super(objectMapper, exceptionHandler);

        this.AccountService = AccountService;

    }

    @Override
    protected void execute(HttpExchange exchange) throws IOException, InterruptedException {

        byte[] response;

        if (exchange.getRequestMethod().equals("GET")) {

            final String query = exchange.getRequestURI().getRawQuery();

            if (query == null)

                throw new BadRequestException(StatusCode.BAD_REQUEST.getCode(), "query not found");

            final Map<String, String> params = Util.queryToMap(query);

            if (params == null)

                throw new BadRequestException(StatusCode.BAD_REQUEST.getCode(), "query not found");

            final String accountIdKey = "id";

            if (!params.containsKey(accountIdKey))

                throw new BadRequestException(StatusCode.BAD_REQUEST.getCode(), "required params not found");

            ResponseEntity entity;

            try {

                entity = doGet(Long.parseLong(params.get(accountIdKey)));

            } catch (NumberFormatException e) {

                throw new BadRequestException(StatusCode.BAD_REQUEST.getCode(), "invalid account id");

            }

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

    private ResponseEntity<ReadResponse> doGet(long accountId) throws InterruptedException {

        final Account account = AccountService.read(accountId);

        if (account == null)

            throw ApplicationExceptions.notFound("account (id " + accountId + ") not found").get();

        final ReadResponse response = new ReadResponse(account.getName(), account.getFunds());

        return new ResponseEntity<>(response, getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

    }

}
