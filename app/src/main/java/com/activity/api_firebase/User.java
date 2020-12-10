package com.activity.api_firebase;

import java.io.Serializable;

public class User implements Serializable {
    private String lastName, FirstName, gender, salary;
    private String id;

    public User(String lastName, String firstName, String gender, String salary, String id) {
        this.lastName = lastName;
        FirstName = firstName;
        this.gender = gender;
        this.salary = salary;
        this.id = id;
    }

    public User() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
