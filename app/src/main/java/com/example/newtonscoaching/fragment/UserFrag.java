package com.example.newtonscoaching.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newtonscoaching.R;
import com.example.newtonscoaching.adapter.UsersAdapter;
import com.example.newtonscoaching.api.RetrofitClient;
import com.example.newtonscoaching.model.User;
import com.example.newtonscoaching.model.UserResp;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFrag extends Fragment {

    private RecyclerView recyclerView;
    private UsersAdapter adapter;
    private List<User> userList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        Call<UserResp> call = RetrofitClient.getInstance().getApi().getAllStudent();
        call.enqueue(new Callback<UserResp>() {
            @Override
            public void onResponse(Call<UserResp> call, Response<UserResp> response) {

                userList = response.body().getUsers();
                adapter = new UsersAdapter(getActivity(), userList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<UserResp> call, Throwable t) {

            }
        });
    }
}
