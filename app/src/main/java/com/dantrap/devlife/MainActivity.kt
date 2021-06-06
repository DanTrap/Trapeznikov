package com.dantrap.devlife

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.MarginPageTransformer
import com.dantrap.devlife.databinding.ActivityMainBinding
import com.dantrap.devlife.repository.Repository
import com.dantrap.devlife.fragments.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    private lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val tabNames = listOf("Random", "Latest", "Hot", "Top")

        val fragmentList = arrayListOf(
            RandomFragment(),
            LatestFragment(),
            HotFragment(),
            TopFragment()
        )

        binding.viewPager.adapter = PagerAdapter(
            fragmentList,
            supportFragmentManager,
            lifecycle
        )

        binding.backImageButton.imageAlpha

        binding.viewPager.setPageTransformer(MarginPageTransformer(24))

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.backImageButton.isClickable = when (tab?.text.toString()) {
                    "Random" -> viewModel.currentRandomGifIndex.value != 0
                    "Latest" -> viewModel.currentLatestGifIndex.value != 0
                    "Hot" -> viewModel.currentHotGifIndex.value != 0
                    else -> viewModel.currentTopGifIndex.value != 0
                }
                setImageAlpha()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.forwardImageButton.setOnClickListener {
               when (binding.tabLayout.selectedTabPosition) {
                    0 -> viewModel.setRandomGif(isInternetAvailable())
                    1 -> viewModel.setLatestGif(isInternetAvailable())
                    2 -> viewModel.setHotGif(isInternetAvailable())
                    3 -> viewModel.setTopGif(isInternetAvailable())
                }
        }

        binding.backImageButton.setOnClickListener {
            when (binding.tabLayout.selectedTabPosition) {
                0 -> viewModel.setPrevRandomGif()
                1 -> viewModel.setPrevLatestGif()
                2 -> viewModel.setPrevHotGif()
                3 -> viewModel.setPrevTopGif()
            }
        }

        viewModel.currentRandomGifIndex.observe(this, {
            binding.backImageButton.isClickable = it > 0
            setImageAlpha()
        })

        viewModel.currentLatestGifIndex.observe(this, {
            binding.backImageButton.isClickable = it > 0
            setImageAlpha()
        })

        viewModel.currentHotGifIndex.observe(this, {
            binding.backImageButton.isClickable = it > 0
            setImageAlpha()
        })

        viewModel.currentTopGifIndex.observe(this, {
            binding.backImageButton.isClickable = it > 0
            setImageAlpha()
        })

        // Network monitoring
        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                if (!viewModel.isAppWork) setGifs()
                GlobalScope.launch(Dispatchers.Main) { supportActionBar?.title = "Developers Life" }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                GlobalScope.launch(Dispatchers.Main) { supportActionBar?.title = "Connection lost..." }
            }
        })
    }

    private fun setGifs() {
        viewModel.isAppWork = true
        viewModel.getRandomGif()
        viewModel.getLatestGif()
        viewModel.getHotGif()
        viewModel.getTopGif()
    }

    private fun setImageAlpha() {
        if (binding.backImageButton.isClickable) binding.backImageButton.imageAlpha = 255
        else binding.backImageButton.imageAlpha = 75
    }

    private fun isInternetAvailable(): Boolean {
        connectivityManager.activeNetworkInfo.also {
            return  it != null && it.isConnected
        }
    }
}