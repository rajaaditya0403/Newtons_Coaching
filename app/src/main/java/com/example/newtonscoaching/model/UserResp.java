package com.example.newtonscoaching.model;

import java.util.List;

public class UserResp {
    private boolean error;
    private List<User> users;

    public boolean isError() {
        return error;
    }

    public List<User> getUsers() {
        return users;
    }
}
