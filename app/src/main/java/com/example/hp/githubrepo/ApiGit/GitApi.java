package com.example.hp.githubrepo.ApiGit;

import com.example.hp.githubrepo.Beans.GitBeans;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;



public interface GitApi
{
    @GET("users/{name}")
    Call<GitBeans> getDetails(@Path("name") String name);


}
