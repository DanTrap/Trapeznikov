package com.dantrap.devlife.api

import com.dantrap.devlife.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        val factory = GsonConverterFactory.create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(factory)
            .build()
    }

    val api: Api by lazy {
        retrofit.create(Api::class.java)
    }
}