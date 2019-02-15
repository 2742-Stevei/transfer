package com.example.transfer;

import java.io.Serializable;

public class AccountBean implements Serializable {

    private Long id;

    private String name;

    private Long funds;

    Long getId() {

        return id;

    }

    String getName() {

        return name;

    }

    Long getFunds() {

        return funds;

    }

}
