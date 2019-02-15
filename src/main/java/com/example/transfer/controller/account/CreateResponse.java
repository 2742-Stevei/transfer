package com.example.transfer.controller.account;

public final class CreateResponse {

    private final long id;

    @java.beans.ConstructorProperties({"id"})
    CreateResponse(Long id) {

        this.id = id;

    }

    public long getId() {

        return id;

    }

}
