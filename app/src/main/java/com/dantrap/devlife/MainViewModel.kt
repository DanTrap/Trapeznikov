package com.dantrap.devlife

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dantrap.devlife.model.Gif
import com.dantrap.devlife.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application) {

    var isAppWork = false

    // Random GIFs

    private val randomGifResponse: ArrayList<Gif> = ArrayList()
    var currentRandomGifIndex = MutableLiveData<Int>().apply { value = 0 }
    val currentRandomGif: MutableLiveData<Gif> = MutableLiveData()

    fun getRandomGif() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getRandomGif()
                randomGifResponse.add(response)
                currentRandomGif.postValue(response)
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    currentRandomGifIndex.value?.let { i -> currentRandomGifIndex.value = i - 1 }
                }
            }
        }
    }

    fun setRandomGif(isInternetAvailable: Boolean) {
        currentRandomGifIndex.value?.let { i -> currentRandomGifIndex.value = i + 1 }
        Log.d("BOI", "----------------------------------------------")
        Log.d("BOI", "index do if = ${currentRandomGifIndex.value}")
        if (currentRandomGifIndex.value!! >= randomGifResponse.size) {
            if (isInternetAvailable) {
                getRandomGif()
            } else currentRandomGifIndex.value?.let { i -> currentRandomGifIndex.value = i - 1 }
        } else {
            currentRandomGif.value = randomGifResponse[currentRandomGifIndex.value!!]
        }
        Log.d("BOI", "index posle if = ${currentRandomGifIndex.value}")
    }

    fun setPrevRandomGif() {
        if (currentRandomGifIndex.value!! <= randomGifResponse.size) {
            currentRandomGifIndex.value?.let { i -> currentRandomGifIndex.value = i - 1 }
            currentRandomGif.value = randomGifResponse[currentRandomGifIndex.value!!]
        }
    }

    // Latest GIFs

    private val latestGifResponse: ArrayList<Gif> = ArrayList()
    private var currentLatestGifPage: Int = 0
    var currentLatestGifIndex = MutableLiveData<Int>().apply { value = 0 }
    val currentLatestGif: MutableLiveData<Gif> = MutableLiveData()

    fun getLatestGif() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getLatestGif(currentLatestGifPage)
                latestGifResponse.addAll(response.result)
                currentLatestGif.postValue(response.result[0])
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    currentLatestGifIndex.value?.let { i -> currentLatestGifIndex.value = i - 1 }
                }
            }
        }
    }

    fun setLatestGif(isInternetAvailable: Boolean) {
        currentLatestGifIndex.value?.let { i -> currentLatestGifIndex.value = i + 1 }
        if (currentLatestGifIndex.value!! >= latestGifResponse.size) {
            if (isInternetAvailable) {
                ++currentLatestGifPage
                getLatestGif()
            } else currentLatestGifIndex.value?.let { i -> currentLatestGifIndex.value = i - 1 }
        } else {
            currentLatestGif.value = latestGifResponse[currentLatestGifIndex.value!!]
        }
    }

    fun setPrevLatestGif() {
        if (currentLatestGifIndex.value!! < latestGifResponse.size) {
            currentLatestGifIndex.value?.let { i -> currentLatestGifIndex.value = i - 1 }
            currentLatestGif.value = latestGifResponse[currentLatestGifIndex.value!!]
        }
    }

    // Hot GIFs

    private val hotGifResponse: ArrayList<Gif> = ArrayList()
    private var currentHotGifPage: Int = 0
    var currentHotGifIndex = MutableLiveData<Int>().apply { value = 0 }
    val currentHotGif: MutableLiveData<Gif> = MutableLiveData()

    fun getHotGif() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getHotGif(currentHotGifPage)
                if (response.result.isNotEmpty()) {
                    hotGifResponse.addAll(response.result)
                    currentHotGif.postValue(response.result[0])
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    currentHotGifIndex.value?.let { i -> currentHotGifIndex.value = i - 1 }
                }
            }
        }
    }

    fun setHotGif(isInternetAvailable: Boolean) {
        currentHotGifIndex.value?.let { i -> currentHotGifIndex.value = i + 1 }

        if (currentHotGifIndex.value!! >= hotGifResponse.size) {
            if (isInternetAvailable) {
                ++currentHotGifPage
                getHotGif()
            } else currentHotGifIndex.value?.let { i -> currentHotGifIndex.value = i - 1 }
        } else {
            currentHotGif.value = hotGifResponse[currentHotGifIndex.value!!]
        }
    }

    fun setPrevHotGif() {
        if (currentHotGifIndex.value!! < hotGifResponse.size) {
            currentHotGifIndex.value?.let { i -> currentHotGifIndex.value = i - 1 }
            currentHotGif.value = hotGifResponse[currentHotGifIndex.value!!]
        }
    }

    // Top GIFs

    private val topGifResponse: ArrayList<Gif> = ArrayList()
    private var currentTopGifPage: Int = 0
    var currentTopGifIndex = MutableLiveData<Int>().apply { value = 0 }
    val currentTopGif: MutableLiveData<Gif> = MutableLiveData()

    fun getTopGif() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getTopGif(currentTopGifPage)
                topGifResponse.addAll(response.result)
                currentTopGif.postValue(response.result[0])
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    currentTopGifIndex.value?.let { i -> currentTopGifIndex.value = i - 1 }
                }
            }
        }
    }

    fun setTopGif(isInternetAvailable: Boolean) {
        currentTopGifIndex.value?.let { i -> currentTopGifIndex.value = i + 1 }
        if (currentTopGifIndex.value!! >= topGifResponse.size) {
            if (isInternetAvailable) {
                ++currentTopGifPage
                getTopGif()
            } else currentTopGifIndex.value?.let { i -> currentTopGifIndex.value = i - 1 }
        } else {
            currentTopGif.value = topGifResponse[currentTopGifIndex.value!!]
        }
    }

    fun setPrevTopGif() {
        if (currentTopGifIndex.value!! < topGifResponse.size) {
            currentTopGifIndex.value?.let { i -> currentTopGifIndex.value = i - 1 }
            currentTopGif.value = topGifResponse[currentTopGifIndex.value!!]
        }
    }
}