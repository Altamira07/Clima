package com.example.luisito.clima;

import com.example.luisito.clima.api.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by luisito on 19/11/17.
 */

public interface ClimaService
{

    @GET
    Call<Api> api (@Url String url);
}
