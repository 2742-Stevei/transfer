package com.example.transfer.controller.account;

public final class TransferRequest {

    private final long idFrom;

    private final long idTo;

    private final long fundsToTransfer;

    @java.beans.ConstructorProperties({"idFrom", "idTo", "fundsToTransfer"})
    public TransferRequest(long idFrom, long idTo, long fundsToTransfer) {

        this.idFrom = idFrom;

        this.idTo = idTo;

        this.fundsToTransfer = fundsToTransfer;

    }

    long getIdFrom() {

        return idFrom;

    }

    long getIdTo() {

        return idTo;

    }

    long getFundsToTransfer() {

        return fundsToTransfer;

    }

}
