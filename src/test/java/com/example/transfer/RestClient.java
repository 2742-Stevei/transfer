package com.example.transfer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

class RestClient {

    static HttpResponse sendGet(String url) throws IOException {

        return getHttpClient().execute(makeGetRequest(url));

    }

    static HttpResponse sendPost(String url, String data) throws IOException {

        return getHttpClient().execute(makePostRequest(url, new StringEntity(data)));

    }

    static HttpResponse sendPut(String url, String data) throws IOException {

        return getHttpClient().execute(makePutRequest(url, new StringEntity(data)));

    }

    private static HttpGet makeGetRequest(String url) {

        final HttpGet request = new HttpGet(url);

        addHeaders(request);

        return request;

    }

    private static HttpPost makePostRequest(String url, HttpEntity params) {

        HttpPost request = new HttpPost(url);

        addHeaders(request);

        request.setEntity(params);

        return request;

    }

    private static HttpPut makePutRequest(String url, HttpEntity params) {

        HttpPut request = new HttpPut(url);

        addHeaders(request);

        request.setEntity(params);

        return request;

    }

    private static void addHeaders(HttpRequestBase request) {

        request.addHeader("Content-Type", "application/x-www-form-urlencoded");

        request.addHeader("Accept", "*/*");

    }

    private static HttpClient getHttpClient() {

        return HttpClientBuilder.create().build();

    }

}
