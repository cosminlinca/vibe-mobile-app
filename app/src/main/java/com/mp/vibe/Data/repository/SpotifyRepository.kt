package com.mp.vibe.Data.repository

import android.content.Context
import com.mp.vibe.Config.RetrofitFactory
import com.mp.vibe.Data.db.UserDatabase
import com.mp.vibe.Data.model.Track
import com.mp.vibe.Data.model.TracksResponse
import com.mp.vibe.Webservice.SpotifyAPI
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response

class SpotifyRepository(private var context: Context) {

    private var spotifyAPI: SpotifyAPI

    init {
        this.context = context
        this.spotifyAPI = RetrofitFactory.retrofit_basic.create(SpotifyAPI::class.java)
    }

    /**
     * Documentation: https://developer.spotify.com/documentation/web-api/reference/tracks/get-track/
     */
    fun getTrack(id: String): Single<Response<Track>> {
        var db = UserDatabase.getInstance(context!!)
        val access_token = db.authResponseDao().all[0].access_token
        val header = HashMap<String, String>()
        header["Authorization"] = "Bearer $access_token"

        return spotifyAPI.getTrack(id, header)
    }


    /**
     * Documentation: https://developer.spotify.com/documentation/web-api/reference/tracks/get-several-tracks/
     */
    fun getTracks(ids: String): Single<Response<TracksResponse>>{
        var db = UserDatabase.getInstance(context!!)
        val access_token = db.authResponseDao().all[0].access_token
        val header = HashMap<String, String>()
        header["Authorization"] = "Bearer $access_token"

        return spotifyAPI.getTracks(ids, header)
    }


    fun play(song: String): Completable {
        var db = UserDatabase.getInstance(context!!)
        val access_token = db.authResponseDao().all[0].access_token
        val header = HashMap<String, String>()
        header["Authorization"] = "Bearer $access_token"

        return spotifyAPI.play(song, header)
    }

}