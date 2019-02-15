package com.example.transfer.model;

import java.util.Objects;

public class Account {

    private final long id;

    private final String name;

    private long funds;

    @java.beans.ConstructorProperties({"id", "name", "funds"})
    public Account(long id, String name, long funds) {

        this.id = id;

        this.name = name;

        this.funds = funds;

    }

    public long getId() {

        return id;

    }

    public String getName() {

        return name;

    }

    public long getFunds() {

        return funds;

    }

    public void changeFunds(long delta) {

        funds += delta;

    }

    @java.lang.Override
    public boolean equals(Object o) {

        if (o == this) return true;

        if (!(o instanceof Account)) return false;

        final Account other = (Account)o;

        if (Objects.equals(this, other)) return false;

        if (this.getId() != other.getId()) return false;

        if (!this.getName().equals(other.getName())) return false;

        return this.getFunds() == other.getFunds();

    }

    @java.lang.Override
    public int hashCode() {

        final int PRIME = 59;

        int result = 1;

        final Object $name = this.getName();

        result = result * PRIME + ($name == null ? 43 : $name.hashCode());

        result = result * PRIME + (int) this.getId();

        final long $funds = this.getFunds();

        result = result * PRIME + (int)($funds >>> 32 ^ $funds);

        return result;

    }

    @java.lang.Override
    public String toString() {

        return "Account(id=" + getId() + ", name=" + getName() + ", funds=" + getFunds() + ")";

    }

}
