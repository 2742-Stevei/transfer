package com.example.transfer.model;

public interface AccountRepository {

    long create(AccountCreate account) throws InterruptedException;

    Account read(long id) throws InterruptedException;

    long transfer(long idFrom, long idTo, long funds) throws InterruptedException;

}
