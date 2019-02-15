package com.example.transfer;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.transfer.controller.StatusCode;

class ApplicationTest {

    private static Random random = new Random();

    private static Gson gson = new Gson();

    private static AccountPartialBean

            accountCreateFrom = new AccountPartialBean(Util.generateString(8), random.nextLong()),

            accountCreateTo = new AccountPartialBean(Util.generateString(9), random.nextLong());

    @Test
    void createAccount() throws IOException {

        HttpResponse response = RestClient.sendPut(TransferRestApi.getPutUrl(), gson.toJson(accountCreateFrom));

        assertEquals(StatusCode.CREATED.getCode(), response.getStatusLine().getStatusCode());

        AccountIdBean accountIdBean = TransferRestApi.serializeFromHttpResponse(response, AccountIdBean.class);

        assertNotNull(accountIdBean);

        AccountPartialBean accountPartial = TransferRestApi.getAccount(accountIdBean.getId());

        assertEquals(accountCreateFrom.getName(), accountPartial.getName());

        assertEquals(accountCreateFrom.getFunds(), accountPartial.getFunds());

    }

    @Test
    void getAccount() throws IOException {

        long accountId = TransferRestApi.createAccount(accountCreateFrom.getName(), 100);

        HttpResponse response = RestClient.sendGet(TransferRestApi.getGetUrl(accountId));

        assertEquals(StatusCode.OK.getCode(), response.getStatusLine().getStatusCode());

    }

    @Test
    void transferBetweenAccounts() throws IOException {

        long

                accountIdFrom = TransferRestApi.createAccount(accountCreateFrom.getName(), 100),

                accountIdTo = TransferRestApi.createAccount(accountCreateTo.getName(), 300),

                fundsToTransfer = 50;

        AccountPartialBean

                accountPartialFrom = TransferRestApi.getAccount(accountIdFrom),

                accountPartialTo = TransferRestApi.getAccount(accountIdTo);

        long transactionId = TransferRestApi.transferBetweenAccount(accountIdFrom, accountIdTo, fundsToTransfer);

        assertTrue(transactionId >= 0L);

        AccountPartialBean

                accountPartialFromAfter = TransferRestApi.getAccount(accountIdFrom),

                accountPartialToAfter = TransferRestApi.getAccount(accountIdTo);

        assertEquals(accountPartialFrom.getFunds() - fundsToTransfer, accountPartialFromAfter.getFunds());

        assertEquals(accountPartialTo.getFunds() + fundsToTransfer, accountPartialToAfter.getFunds());

    }

    @Test
    void transferInsufficientFunds() throws IOException {

        long

                accountIdFrom = TransferRestApi.createAccount(accountCreateFrom.getName(), 100),

                accountIdTo = TransferRestApi.createAccount(accountCreateTo.getName(), 200),

                fundsToTransfer = 500;

        assertEquals(StatusCode.NOT_ACCEPTABLE.getCode(),  RestClient.sendPost(TransferRestApi.getTransferUrl(), gson.toJson(new TransactionRequestBean(accountIdFrom, accountIdTo, fundsToTransfer))).getStatusLine().getStatusCode());

    }

    @Test
    void accountDoesNotExists() throws IOException {

        HttpResponse response = RestClient.sendGet(TransferRestApi.getGetUrl(Long.MAX_VALUE));

        assertEquals(StatusCode.NOT_FOUND.getCode(), response.getStatusLine().getStatusCode());

    }

    @Test
    void badRequest() throws IOException {

        HttpResponse response = RestClient.sendGet(TransferRestApi.getGetUrl(0L) + "invalid_query");

        assertEquals(StatusCode.BAD_REQUEST.getCode(), response.getStatusLine().getStatusCode());

    }

    @Test
    void methodNotAllowed() throws IOException {

        HttpResponse response = RestClient.sendPost(TransferRestApi.getPutUrl(), "");

        assertEquals(StatusCode.METHOD_NOT_ALLOWED.getCode(), response.getStatusLine().getStatusCode());

    }

}