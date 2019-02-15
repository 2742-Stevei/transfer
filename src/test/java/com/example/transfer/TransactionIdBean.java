package com.example.transfer;

import java.io.Serializable;

class TransactionIdBean implements Serializable {

    private long transactionId;

    private TransactionIdBean(long transactionId) {

        this.transactionId = transactionId;

    }

    Long getTransactionId() {

        return transactionId;

    }

}
