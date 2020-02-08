package com.mp.vibe.Data.repository

import com.mp.vibe.Config.RetrofitFactory
import com.mp.vibe.Data.model.ChartReportItem
import com.mp.vibe.Data.model.Track
import com.mp.vibe.Webservice.AuthAPI
import com.mp.vibe.Webservice.WebAPI
import io.reactivex.Single
import retrofit2.Response

class WebRepository {
    private val webApi: WebAPI;

    init {
        this.webApi = RetrofitFactory.local_retrofit.create(WebAPI::class.java);
    }

    fun getChartReport(): Single<Response<List<ChartReportItem>>> {
        return webApi.getChartReport();
    }

    fun addSong(songName: String, artistName: String): Single<Response<Track>> {
        val params = HashMap<String, String>()
        params["songName"] = songName
        params["artistName"] = artistName

        return webApi.addSong(params)
    }
}