package com.example.transfer.common;

import java.util.HashMap;
import java.util.Map;

public class Util {

    private Util(){}

    public static Map<String, String> queryToMap(String query) {

        final Map<String, String> result = new HashMap<>();

        for (String param : query.split("&")) {

            final String[] entry = param.split("=");

            result.put(entry[0], entry.length > 1 ? entry[1] : "");

        }

        return result;

    }

}
