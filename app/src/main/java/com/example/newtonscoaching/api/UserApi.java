package com.example.newtonscoaching.api;

import com.example.newtonscoaching.model.DefaultResp;
import com.example.newtonscoaching.model.LoginResp;
import com.example.newtonscoaching.model.UserResp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {
    @FormUrlEncoded
    @POST("registration.php")
    Call<DefaultResp> registration(
            @Field("name") String email,
            @Field("email") String password,
            @Field("password") String name,
            @Field("contact") String school
    );

    @FormUrlEncoded
    @POST("studentlogin.php")
    Call<LoginResp> studentLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("getallstudent.php")
    Call<UserResp> getAllStudent();

    @FormUrlEncoded
    @POST("updatestudent.php")
    Call<LoginResp> updateStudent(
            @Field ("id") int id,
            @Field("email") String name,
            @Field("contact") String school
    );

    @FormUrlEncoded
    @POST("updatepassword.php")
    Call<DefaultResp> updatePassword(
            @Field("currentpassword") String currentpassword,
            @Field("newpassword") String newpassword,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("deletestudent.php")
    Call<DefaultResp> deleteStudent(
            @Field ("id") int id
    );
}
