package com.dantrap.devlife.api

import com.dantrap.devlife.model.Gif
import com.dantrap.devlife.model.Result
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("random?json=true")
    suspend fun getRandomGif(): Gif

    @GET("latest/{page}?json=true")
    suspend fun getLatestGif(@Path("page") page: Int): Result

    @GET("hot/{page}?json=true")
    suspend fun getHotGif(@Path("page") page: Int): Result

    @GET("top/{page}?json=true")
    suspend fun getTopGif(@Path("page") page: Int): Result

}