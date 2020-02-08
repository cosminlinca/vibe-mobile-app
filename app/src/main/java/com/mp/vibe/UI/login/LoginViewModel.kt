package com.mp.vibe.UI.login

import android.content.Context
import android.os.Build
import android.util.Log
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mp.vibe.Data.repository.AuthRepository
import com.mp.vibe.Data.repository.LoginRepository
import com.mp.vibe.Data.db.UserDatabase
import com.mp.vibe.Data.model.AuthResponse
import com.mp.vibe.Data.model.LoggedInUser
import com.mp.vibe.R
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class LoginViewModel(private val loginRepository: LoginRepository,
                     private val context: Context
) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(email: String, password: String) {

        // can be launched in a separate asynchronous job
        val result = loginRepository.login(email, password)
        // Response
        val reqResponse = object : SingleObserver<Response<LoggedInUser>> {

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onSuccess(loginResponse: Response<LoggedInUser>) {
                if (loginResponse.isSuccessful) {

                    // Get local DB (Created with Room)
                    var localDB = UserDatabase.getInstance(context)

                    // Client Credentials Flow
                    var authRepo =
                        AuthRepository(context)
                    val singleResponse = object : SingleObserver<Response<AuthResponse>> {
                        override fun onSuccess(spotifyAuthTokenResponse: Response<AuthResponse>) {
                            if (spotifyAuthTokenResponse != null) {
                                if (spotifyAuthTokenResponse.isSuccessful) {
                                    Log.i("Access token", spotifyAuthTokenResponse.body()?.access_token)
                                    var authResponse = spotifyAuthTokenResponse.body()!!
                                    // Delete all tokens
                                    // localDB.authResponseDao().deleteAll()

                                    // Add new token
                                    localDB.authResponseDao()?.insert(authResponse)


                                    // Succes
                                    _loginResult.value =
                                        LoginResult(success = LoggedInUserView(displayName = loginResponse.body()?.firstName!!))

                                } else {
                                    Log.e("Access token Error", spotifyAuthTokenResponse.errorBody().toString())
                                    _loginResult.value = LoginResult(error = R.string.login_failed)
                                }
                            }
                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onError(e: Throwable) {
                            Log.e("Spotify request error", e?.message)
                            _loginResult.value = LoginResult(error = R.string.login_failed)
                        }

                    }

                    // Get token
                    authRepo.token()
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe(singleResponse)
                }
                else {
                    Log.e("Login unsuccessful", loginResponse.errorBody()?.string())
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {
                Log.e("Login error", e.toString())
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }

        result.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(reqResponse)
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 5
    }
}
