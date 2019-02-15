package com.example.transfer.controller.account;

public final class ReadResponse {

    private final String name;

    private final long funds;

    @java.beans.ConstructorProperties({"name", "funds"})
    ReadResponse(String name, long funds) {

        this.name = name;

        this.funds = funds;

    }

    public String getName() {

        return name;

    }

    public long getFunds() {

        return funds;

    }

}
