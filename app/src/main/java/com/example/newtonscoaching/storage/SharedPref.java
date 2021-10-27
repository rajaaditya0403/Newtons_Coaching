package com.example.newtonscoaching.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.newtonscoaching.model.User;

public class SharedPref {
    private static final String SHARED_PREF = "shared_pref";

    private static SharedPref mInst;
    private Context ctx;

    private SharedPref(Context ctx) {
        this.ctx = ctx;
    }


    public static synchronized SharedPref getInstance(Context ctx) {
        if (mInst == null) {
            mInst = new SharedPref(ctx);
        }
        return mInst;
    }


    public void saveUser(User user) {

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", user.getId());
        editor.putString("email", user.getEmail());
        editor.putString("name", user.getName());
        editor.putString("contact", user.getContact());

        editor.apply();

    }

    public boolean loggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("contact", null)
        );
    }

    public void delete() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
