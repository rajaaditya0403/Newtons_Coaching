package com.example.newtonscoaching.model;

import com.google.gson.annotations.SerializedName;

public class DefaultResp {
    @SerializedName("error")
    private boolean err;

    @SerializedName("message")
    private String msg;

    public DefaultResp(boolean err, String msg) {
        this.err = err;
        this.msg = msg;
    }

    public boolean isErr() {
        return err;
    }

    public String getMsg() {
        return msg;
    }
}
