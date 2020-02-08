package com.mp.vibe.Config

import com.google.gson.GsonBuilder
import com.mp.vibe.Utils.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory private constructor() {
    companion object {
        val auth_retrofit: Retrofit
            get() {
                val gsonBuilder = GsonBuilder()
                gsonBuilder.registerTypeAdapterFactory(ItemTypeAdapterFactory())

                var builder: Retrofit.Builder = Retrofit.Builder()
                    .baseUrl(Constants.ACCOUNT_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                return builder.build()
            }

        val retrofit_basic: Retrofit
            get() {
                val gsonBuilder = GsonBuilder()
                gsonBuilder.registerTypeAdapterFactory(ItemTypeAdapterFactory())

                var builder: Retrofit.Builder = Retrofit.Builder()
                    .baseUrl(Constants.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                return builder.build()
            }

        val local_retrofit: Retrofit
            get() {
                val gsonBuilder = GsonBuilder()
                gsonBuilder.registerTypeAdapterFactory(ItemTypeAdapterFactory())

                var builder: Retrofit.Builder = Retrofit.Builder()
                    .baseUrl(Constants.LOCAL_URI)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                return builder.build()
            }
    }
}