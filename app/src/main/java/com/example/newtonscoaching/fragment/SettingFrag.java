package com.example.newtonscoaching.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newtonscoaching.R;
import com.example.newtonscoaching.activity.LoginActivity;
import com.example.newtonscoaching.activity.MainActivity;
import com.example.newtonscoaching.api.RetrofitClient;
import com.example.newtonscoaching.model.DefaultResp;
import com.example.newtonscoaching.model.LoginResp;
import com.example.newtonscoaching.model.User;
import com.example.newtonscoaching.storage.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingFrag extends Fragment implements View.OnClickListener  {

    private EditText editTextEmail, editTextContact;
    private EditText editTextCurrentPassword, editTextNewPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setting_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextEmail = view.findViewById(R.id.frEmail);
        editTextContact = view.findViewById(R.id.frContact);
        editTextCurrentPassword = view.findViewById(R.id.frCurrentPassword);
        editTextNewPassword = view.findViewById(R.id.frNewPassword);

        view.findViewById(R.id.buttonSave).setOnClickListener(this);
        view.findViewById(R.id.buttonChangePassword).setOnClickListener(this);
        view.findViewById(R.id.buttonLogout).setOnClickListener(this);
        view.findViewById(R.id.buttonDelete).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSave:
                updateProfile();
                break;
            case R.id.buttonChangePassword:
                updatePassword();
                break;
            case R.id.buttonLogout:
                logout();
                break;
            case R.id.buttonDelete:
                deleteUser();
                break;
        }
    }

    //video 19
    private void updateProfile() {
        String email = editTextEmail.getText().toString().trim();
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
        if (contact.isEmpty()) {
            editTextContact.setError("School required");
            editTextContact.requestFocus();
            return;
        }
        User user = SharedPref.getInstance(getActivity()).getUser();

        Call<LoginResp> call = RetrofitClient.getInstance()
                .getApi().updateStudent(
                        user.getId(),
                        email,
                        contact
                );
        call.enqueue(new Callback<LoginResp>() {
            @Override
            public void onResponse(Call<LoginResp> call, Response<LoginResp> response) {

                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                if (!response.body().isError()) {
                    SharedPref.getInstance(getActivity()).saveUser(response.body().getUser());
                }
            }

            @Override
            public void onFailure(Call<LoginResp> call, Throwable t) {

            }
        });
    }

    // video 20
    private void updatePassword() {
        String currentpassword = editTextCurrentPassword.getText().toString().trim();
        String newpassword = editTextNewPassword.getText().toString().trim();

        if (currentpassword.isEmpty()) {
            editTextCurrentPassword.setError("Password required");
            editTextCurrentPassword.requestFocus();
            return;
        }

        if (newpassword.isEmpty()) {
            editTextNewPassword.setError("Enter new password");
            editTextNewPassword.requestFocus();
            return;
        }


        User user = SharedPref.getInstance(getActivity()).getUser();

        Call<DefaultResp> call = RetrofitClient.getInstance().getApi()
                .updatePassword(currentpassword, newpassword, user.getEmail());

        call.enqueue(new Callback<DefaultResp>() {
            @Override
            public void onResponse(Call<DefaultResp> call, Response<DefaultResp> response) {
                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<DefaultResp> call, Throwable t) {

            }
        });
    }

    //video 21
    private void logout() {
        SharedPref.getInstance(getActivity()).delete();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //video 22
    private void deleteUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure?");
        builder.setMessage("This action is irreversible...");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                User user = SharedPref.getInstance(getActivity()).getUser();
                Call<DefaultResp> call = RetrofitClient.getInstance().getApi().deleteStudent(user.getId());

                call.enqueue(new Callback<DefaultResp>() {
                    @Override
                    public void onResponse(Call<DefaultResp> call, Response<DefaultResp> response) {

                        if (!response.body().isErr()) {
                            SharedPref.getInstance(getActivity()).delete();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<DefaultResp> call, Throwable t) {

                    }
                });

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog ad = builder.create();
        ad.show();
    }
}
