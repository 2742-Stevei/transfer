package com.example.transfer.model;

public class AccountCreate {

    private final String name;

    private final long funds;

    public AccountCreate(String name, long funds) {

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
