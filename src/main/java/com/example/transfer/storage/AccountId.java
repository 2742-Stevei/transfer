package com.example.transfer.storage;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class AccountId implements Comparable<AccountId> {

    private Long id;

    private Lock lock = new ReentrantLock();

    AccountId(long id) {

        this.id = id;

    }

    @Override
    public int compareTo(AccountId other) {

        return Long.compare(this.id, other.id);

    }

    @Override
    public int hashCode() {

        return Objects.hash(id);

    }

    @Override
    public boolean equals(Object other) {

        if (other == this)

            return true;

        if (!(other instanceof AccountId))

            return false;

        return ((AccountId) other).id.equals(id);
    }

    Long getId() {

        return id;

    }

    Lock getLock() {

        return lock;

    }

    @Override
    public String toString() {

        return "{id: " + id + "; lock: " + lock + "}";

    }

}
