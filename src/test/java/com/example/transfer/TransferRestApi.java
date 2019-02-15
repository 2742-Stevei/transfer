package com.example.transfer;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;

import java.io.*;

class TransferRestApi {

    private static Gson gson = new Gson();

    static AccountPartialBean getAccount(long id) throws IOException {

        final HttpResponse response = RestClient.sendGet(getGetUrl(id));

        return serializeFromHttpResponse(response, AccountPartialBean.class);

    }

    static long createAccount(String name, long funds) throws IOException {

        return serializeFromHttpResponse(RestClient.sendPut(getPutUrl(), gson.toJson(new AccountPartialBean(name, funds))), AccountIdBean.class).getId();

    }

    static long transferBetweenAccount(long idFrom, long idTo, long fundsToTransfer) throws IOException {

        return serializeFromHttpResponse(RestClient.sendPost(getTransferUrl(), gson.toJson(new TransactionRequestBean(idFrom, idTo, fundsToTransfer))), TransactionIdBean.class).getTransactionId();

    }

    static String getTransferUrl() {

        return "http://localhost:8000/api/account/transfer";

    }

    static String getPutUrl() {

        return "http://localhost:8000/api/account/create";

    }

    static String getGetUrl(long id) {

        return "http://localhost:8000/api/account/read?id=" + id;

    }

    static <T> T serializeFromHttpResponse(HttpResponse httpResponse, Class<T> classType) throws IOException {

        final Reader reader = new InputStreamReader(httpResponse.getEntity().getContent());

        final T beanInstance = gson.fromJson(reader, classType);

        reader.close();

        return beanInstance;

    }

}
