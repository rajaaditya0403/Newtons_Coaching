package com.example.newtonscoaching.model;

public class User {
    private int id;
    private String email, name, contact;

    public User(int id, String email, String name, String contact) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }
}
