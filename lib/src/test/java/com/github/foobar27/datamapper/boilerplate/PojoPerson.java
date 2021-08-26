package com.github.foobar27.datamapper.boilerplate;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public final class PojoPerson implements PersonReadWriteAccessors {

    private String firstName;
    private String lastName;
    private String fullName;
    private int age;

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String value) {
        this.firstName = value;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String value) {
        this.lastName = value;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public void setFullName(String value) {
        this.fullName = value;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int value) {
        this.age = value;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("Person")
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("fullName", fullName)
                .add("age", age)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, fullName, age);
    }

    @Override
    public boolean equals(Object t) {
        if (!(t instanceof PojoPerson)) {
            return false;
        }
        PojoPerson that = (PojoPerson) t;
        return Objects.equals(this.firstName, that.firstName)
                && Objects.equals(this.lastName, that.lastName)
                && Objects.equals(this.fullName, that.fullName)
                && this.age == that.age;
    }

}
