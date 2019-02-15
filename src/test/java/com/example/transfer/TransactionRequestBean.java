package com.example.transfer;

import java.io.Serializable;

class TransactionRequestBean implements Serializable {

    private long idFrom;

    private long idTo;

    private long fundsToTransfer;

    TransactionRequestBean(long idFrom, long idTo, long fundsToTransfer) {

        this.idFrom = idFrom;

        this.idTo = idTo;

        this.fundsToTransfer = fundsToTransfer;

    }

    Long getIdFrom() {

        return idFrom;

    }

    Long getIdTo() {

        return idTo;

    }

    Long getFundsToTransfer() {

        return fundsToTransfer;

    }

}
