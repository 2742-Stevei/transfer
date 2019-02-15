package com.example.transfer;

import java.io.Serializable;

class AccountIdBean implements Serializable {

    private Long id;

    private AccountIdBean(long id) {

        this.id = id;

    }

    Long getId() {

        return id;

    }

}
