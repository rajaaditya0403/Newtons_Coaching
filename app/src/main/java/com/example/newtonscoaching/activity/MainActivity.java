package com.example.newtonscoaching.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newtonscoaching.R;
import com.example.newtonscoaching.api.RetrofitClient;
import com.example.newtonscoaching.model.DefaultResp;
import com.example.newtonscoaching.storage.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail, editTextPassword, editTextName, editTextContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.Email);
        editTextPassword = findViewById(R.id.Password);
        editTextName = findViewById(R.id.Name);
        editTextContact = findViewById(R.id.Contact);

        findViewById(R.id.btnReg).setOnClickListener(this);
        findViewById(R.id.tvLogin).setOnClickListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPref.getInstance(this).loggedIn()) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userSignUp() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String contact = editTextContact.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be atleast 6 character long");
            editTextPassword.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return;
        }

        if (contact.isEmpty()) {
            editTextContact.setError("School required");
            editTextContact.requestFocus();
            return;
        }

        Call<DefaultResp> call = RetrofitClient
                .getInstance()
                .getApi()
                .registration(name, email, password, contact);


        call.enqueue(new Callback<DefaultResp>() {
            @Override
            public void onResponse(Call<DefaultResp> call, Response<DefaultResp> response) {

                DefaultResp dr = response.body();
                Toast.makeText(MainActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<DefaultResp> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReg:
                userSignUp();
                break;
            case R.id.tvLogin:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}