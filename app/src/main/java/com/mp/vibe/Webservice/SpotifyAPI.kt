package com.mp.vibe.Webservice

import com.mp.vibe.Data.model.Track
import com.mp.vibe.Data.model.TracksResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface SpotifyAPI {

    @GET("/v1/tracks/{id}")
    fun getTrack(@Path("id") id: String,
                 @HeaderMap headers: Map<String, String>): Single<Response<Track>>


    @GET("/v1/tracks/")
    fun getTracks(@Query("ids") ids: String,
        @HeaderMap headers: Map<String, String>): Single<Response<TracksResponse>>

    @PUT("/v1/me/player/play")
    fun play(@Body context_uri: String,
             @HeaderMap headers: Map<String, String>): Completable

}
