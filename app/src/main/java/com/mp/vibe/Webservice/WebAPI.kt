package com.mp.vibe.Webservice

import com.mp.vibe.Data.model.ChartReportItem
import com.mp.vibe.Data.model.LoggedInUser
import com.mp.vibe.Data.model.Track
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WebAPI {
    // Using local server
    @GET("/report")
    fun getChartReport(): Single<Response<List<ChartReportItem>>>

    // Using local server
    @POST("/addSong")
    fun addSong(@Body params: Map<String, String>): Single<Response<Track>>
}