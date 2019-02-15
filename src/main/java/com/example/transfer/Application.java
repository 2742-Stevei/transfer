package com.example.transfer;

import com.example.transfer.controller.account.TransferHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.example.transfer.common.GlobalExceptionHandler;
import com.example.transfer.controller.account.CreateHandler;
import com.example.transfer.controller.account.ReadHandler;
import com.example.transfer.model.AccountRepository;
import com.example.transfer.model.AccountService;
import com.example.transfer.storage.AccountRepositoryStorage;

public class Application {

    private static final int serverPort = 8000;

    private static final int THREAD_AMOUNT = 100;

    private static final Executor executor = Executors.newFixedThreadPool(THREAD_AMOUNT);

    public static void main(String[] args) throws IOException {

        final HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        final ObjectMapper objectMapper = new ObjectMapper();

        final AccountRepository accountRepository = new AccountRepositoryStorage();

        final AccountService accountService = new AccountService(accountRepository);

        final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler(objectMapper);

        final CreateHandler createHandler = new CreateHandler(accountService, objectMapper, globalExceptionHandler);

        final ReadHandler readHandler = new ReadHandler(accountService, objectMapper, globalExceptionHandler);

        final TransferHandler transferHandler = new TransferHandler(accountService, objectMapper, globalExceptionHandler);

        server.createContext("/api/account/create", createHandler::handle);

        server.createContext("/api/account/read", readHandler::handle);

        server.createContext("/api/account/transfer", transferHandler::handle);

        server.setExecutor(executor);

        server.start();

    }

}
