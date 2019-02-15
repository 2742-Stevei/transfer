package com.example.transfer.controller.account;

public final class TransferResponse {

    private final long transactionId;

    @java.beans.ConstructorProperties({"transactionId"})
    TransferResponse(Long transactionId) {

        this.transactionId = transactionId;

    }

    public long getTransactionId() {

        return transactionId;

    }

}
