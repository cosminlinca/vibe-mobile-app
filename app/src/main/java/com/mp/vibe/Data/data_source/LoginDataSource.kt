package com.mp.vibe.Data.data_source

import com.mp.vibe.Config.RetrofitFactory
import com.mp.vibe.Data.model.LoggedInUser
import com.mp.vibe.Webservice.AuthAPI
import io.reactivex.Single
import retrofit2.Response

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    private var localAuthAPI: AuthAPI

    init {
        localAuthAPI = RetrofitFactory.local_retrofit.create(AuthAPI::class.java)
    }

    fun login(email: String, password: String): Single<Response<LoggedInUser>> {
        val body = HashMap<String, String>()
        body["email"] = email
        body["password"] = password

        val user = localAuthAPI.login(body)
        return user.map { result -> Response.success(result.body()?.get(0)) }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

