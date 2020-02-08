package com.mp.vibe.Webservice

import com.mp.vibe.Data.model.AuthResponse
import com.mp.vibe.Data.model.LoggedInUser

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface AuthAPI {

    @Headers("Content-Type: application/json")
    @GET("/authorize")
    fun authorize(@QueryMap params: Map<String, String>): Single<Response<AuthResponse>>


    @FormUrlEncoded
    @POST("/api/token")
    fun token(@HeaderMap headers: Map<String, String>,
              @Field("grant_type") grant_type: String,
              @QueryMap params: Map<String, String>): Single<Response<AuthResponse>>


    // Using local server
    @POST("/login")
    fun login(@Body params: Map<String, String>): Single<Response<List<LoggedInUser>>>
}
