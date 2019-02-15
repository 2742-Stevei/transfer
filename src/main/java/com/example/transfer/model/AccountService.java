package com.example.transfer.model;

public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {

        this.accountRepository = accountRepository;
    }

    public Long create(AccountCreate account) throws InterruptedException {

        return accountRepository.create(account);

    }

    public Account read(long id) throws InterruptedException {

        return accountRepository.read(id);

    }

    public long transfer(long idFrom, long idTo, long funds) throws InterruptedException {

        return accountRepository.transfer(idFrom, idTo, funds);

    }

}
