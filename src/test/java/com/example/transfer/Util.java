package com.example.transfer;

import org.apache.http.HttpResponse;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Util {

    private static final String characters = "abcdefjhijklmnopqrstuvwxyz.";

    private static final Random random = new Random();

    static void checkResponseCode(HttpResponse httpResponse, int statusCode) {

        assertEquals(statusCode, httpResponse.getStatusLine().getStatusCode());

    }

    static String generateString(int length) {

        char[] text = new char[length];

        for(int i = 0; i < length; i ++)

            text[i] = characters.charAt(random.nextInt(characters.length()));

        return new String(text);

    }

}
