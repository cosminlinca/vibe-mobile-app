package com.mp.vibe

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.mp.vibe.Data.repository.AuthRepository
import com.mp.vibe.Data.db.UserDatabase
import com.mp.vibe.Data.model.AuthResponse
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class StartActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        // Connect to Spotify server
//        Spotify.connect(this) {
//            val intent = Intent(this,
//                MainActivity::class.java)
//            startActivity(intent)
//        }

        // Get local DB (Created with Room)
        var localDB = UserDatabase.getInstance(applicationContext)

        // Client Credentials Flow
        var authRepo =
            AuthRepository(applicationContext)
        val singleResponse = object : SingleObserver<Response<AuthResponse>> {
            override fun onSuccess(resp: Response<AuthResponse>) {
                if (resp != null) {
                    if (resp.isSuccessful) {
                        Log.i("Access token", resp.body()?.access_token)
                        var authResponse = resp.body()!!
                        // Delete all tokens
                        localDB.authResponseDao().deleteAll()
                        // Add new token
                        localDB.authResponseDao()?.insert(authResponse)
                        val intent = Intent(this@StartActivity, MainActivity::class.java)
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@StartActivity).toBundle())
                    } else {
                        Log.e("Access token Error", resp.errorBody().toString())
                    }
                }
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
                Log.e("Spotify request error", e?.message)
            }

        }

        // Get token
        authRepo.token()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(singleResponse)
    }
}
