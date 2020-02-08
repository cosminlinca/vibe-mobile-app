package com.mp.vibe.Data.repository

import android.content.Context
import com.mp.vibe.Config.RetrofitFactory
import com.mp.vibe.Data.model.AuthRequest
import com.mp.vibe.Data.model.AuthResponse
import com.mp.vibe.Utils.Constants
import com.mp.vibe.Webservice.AuthAPI
import io.reactivex.Single
import retrofit2.Response

class AuthRepository(private var context: Context) {

    private var authApi: AuthAPI

    init {
        this.context = context
        this.authApi = RetrofitFactory.auth_retrofit.create(AuthAPI::class.java)
    }

    fun authorize(message: AuthRequest): Single<Response<AuthResponse>>? {
        val params = HashMap<String, String>()
        params["client_id"] = message.client_id
        params["response_type"] = message.response_type
        params["redirect_uri"] = message.redirect_uri

        return authApi.authorize(params)
    }

    fun token(): Single<Response<AuthResponse>>? {
        val header = HashMap<String, String>()
        header["Content-Type"] = "application/x-www-form-urlencoded"
        header["Authorization"] = Constants.AUTHORIZATION
        val grant_type = "client_credentials"

        val params = HashMap<String, String>()
        params["scope"] = "user-modify-playback-state"

        return authApi.token(header, grant_type, params)
    }


}