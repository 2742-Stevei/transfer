package com.example.transfer;

import java.io.Serializable;

class AccountPartialBean implements Serializable {

    private String name;

    private Long funds;

    AccountPartialBean(String name, long funds) {

        this.name = name;

        this.funds = funds;

    }

    String getName() {

        return name;

    }

    Long getFunds() {

        return funds;

    }

    @Override
    public String toString() {

        return "{name: " + name + "; funds: " + funds + "}";

    }

}
