package com.dantrap.devlife.repository

import com.dantrap.devlife.api.RetrofitInstance
import com.dantrap.devlife.model.Gif
import com.dantrap.devlife.model.Result

class Repository {

    suspend fun getRandomGif(): Gif {
        return RetrofitInstance.api.getRandomGif()
    }

    suspend fun getLatestGif(page: Int): Result {
        return RetrofitInstance.api.getLatestGif(page)
    }

    suspend fun getHotGif(page: Int): Result {
        return RetrofitInstance.api.getHotGif(page)
    }

    suspend fun getTopGif(page: Int): Result {
        return RetrofitInstance.api.getTopGif(page)
    }
}