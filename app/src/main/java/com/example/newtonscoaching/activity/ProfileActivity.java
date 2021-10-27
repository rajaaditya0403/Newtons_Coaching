package com.example.newtonscoaching.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.newtonscoaching.R;
import com.example.newtonscoaching.fragment.HomeFrag;
import com.example.newtonscoaching.fragment.SettingFrag;
import com.example.newtonscoaching.fragment.UserFrag;
import com.example.newtonscoaching.storage.SharedPref;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView navigationView = findViewById(R.id.btm_nav);
        navigationView.setOnNavigationItemSelectedListener(this);

        displayFragment(new HomeFrag());
    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

       if (!SharedPref.getInstance(this).loggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch(item.getItemId()){
            case R.id.menu_home:
                fragment = new HomeFrag();
                break;
            case R.id.menu_user:
                fragment = new UserFrag();
                break;
            case R.id.menu_setting:
                fragment = new SettingFrag();
                break;
        }

        if(fragment != null){
            displayFragment(fragment);
        }
        return false;
    }

}