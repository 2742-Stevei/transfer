package com.example.transfer.storage;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;

import com.example.transfer.common.ApplicationExceptions;
import com.example.transfer.common.ErrorMessages;
import com.example.transfer.model.Account;
import com.example.transfer.model.AccountCreate;
import com.example.transfer.model.AccountRepository;

public class AccountRepositoryStorage implements AccountRepository {

    private static final int

            TRY_LOCK_MAX_TIMEOUT_MS = 100,

            CREATE_ACCOUNT_TIMEOUT_MS = 1000,

            GET_ACCOUNT_TIMEOUT_MS = 20000,

            TRANSFER_TIMEOUT_MS = 1000,

            SLEEP_MAX_TIMEOUT_MS = 500;

    private static final Map<Long, Account> accountStorage = new ConcurrentHashMap<>();

    private static final AtomicLong

            accountIdCounter = new AtomicLong(0L),

            transactionIdCounter = new AtomicLong(0L);

    private static final Map<AccountId, Lock> locks = new ConcurrentHashMap<>();

    private static final Random random = new Random();

    @Override
    public long create(AccountCreate accountCreate) throws InterruptedException {

        long id = getNewAccountId();

        final Account account = new Account(id, accountCreate.getName(), accountCreate.getFunds());

        final List<AccountId> accountIdList = Collections.singletonList(new AccountId(id));

        if (!captureLockList(accountIdList, CREATE_ACCOUNT_TIMEOUT_MS))

            throw ApplicationExceptions.gatewayTimeout(ErrorMessages.PLEASE_REPEAT).get();

        try {

            accountStorage.putIfAbsent(id, account);

        } finally {

            releaseLockList(accountIdList);

        }

        return id;

    }

    @Override
    public Account read(long id) throws InterruptedException {

        final List<AccountId> accountIdList = Collections.singletonList(new AccountId(id));

        if (!captureLockList(accountIdList, GET_ACCOUNT_TIMEOUT_MS))

            throw ApplicationExceptions.gatewayTimeout(ErrorMessages.PLEASE_REPEAT).get();

        Account result;

        try {

             result = accountStorage.getOrDefault(id, null);

        } finally {

            releaseLockList(accountIdList);

        }

        return result;
    }

    @Override
    public long transfer(long idFrom, long idTo, long fundsToTransfer) throws InterruptedException {

        final List<AccountId> accountIdList = Arrays.asList(new AccountId(idFrom), new AccountId(idTo));

        if (!captureLockList(accountIdList, TRANSFER_TIMEOUT_MS))

            throw ApplicationExceptions.gatewayTimeout(ErrorMessages.PLEASE_REPEAT).get();

        try {

            final Account

                    accountFrom = accountStorage.getOrDefault(idFrom, null),

                    accountTo = accountStorage.getOrDefault(idTo, null);

            if (accountFrom == null || accountTo == null) {

                releaseLockList(accountIdList);

                throw ApplicationExceptions.notFound(ErrorMessages.ACCOUNT_NOT_FOUND).get();
            }

            if (accountFrom.getFunds() < fundsToTransfer) {

                releaseLockList(accountIdList);

                throw ApplicationExceptions.notAcceptable().get();
            }

            if (accountTo.getFunds() > Long.MAX_VALUE - fundsToTransfer) {

                releaseLockList(accountIdList);

                throw ApplicationExceptions.notFound(ErrorMessages.ACCOUNT_TOO_RICH).get();

            }

            accountFrom.changeFunds(-fundsToTransfer);

            accountTo.changeFunds(fundsToTransfer);

        } finally {

            releaseLockList(accountIdList);

        }

        return getNewTransactionId();

    }

    private boolean captureLockList(final List<AccountId> accountIdList, int timeout) throws InterruptedException {

        long deadlineTime = System.currentTimeMillis() + timeout;

        Collections.sort(accountIdList);

        while (System.currentTimeMillis() < deadlineTime) {

            boolean lockWasFailed = false;

            final List<AccountId> lockedSuccesfully = new ArrayList<>();

            for (AccountId accountId : accountIdList) {

                Lock lock = locks.putIfAbsent(accountId, accountId.getLock());

                if (lock == null)

                    lock = locks.get(accountId);

                if (!lock.tryLock(TRY_LOCK_MAX_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {

                    lockWasFailed = true;

                    if (lockedSuccesfully.size() > 0)

                        releaseLockList(lockedSuccesfully);

                    break;

                } else

                    lockedSuccesfully.add(accountId);

            }

            if (!lockWasFailed)

                return true;

            Thread.sleep(getRandomSleepDelay());

        }

        return false;

    }

    private void releaseLockList(final List<AccountId> accountIdList) {

        Collections.sort(accountIdList);

        for (AccountId accountId : accountIdList) {

            try {

                final Lock lock = locks.remove(accountId);

                if (lock != null)

                    lock.unlock();

            } catch (RuntimeException e) {

                e.printStackTrace();

                throw new IllegalStateException(ErrorMessages.UNEXPECTED_UNLOCK_EXCEPTION);

            }


        }

    }

    private long getNewTransactionId() {

        return System.nanoTime() + transactionIdCounter.getAndIncrement();

    }

    private long getNewAccountId() {

        return accountIdCounter.getAndIncrement();

    }

    private int getRandomSleepDelay() {

        return random.nextInt(SLEEP_MAX_TIMEOUT_MS - 1) + 1;

    }

}
