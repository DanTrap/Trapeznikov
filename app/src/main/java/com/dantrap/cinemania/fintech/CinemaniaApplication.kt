package com.dantrap.cinemania.fintech

import android.app.Application
import com.dantrap.cinemania.fintech.core.common.di.DispatchersModule
import com.dantrap.cinemania.fintech.core.data.di.DataModule
import com.dantrap.cinemania.fintech.core.database.di.DatabaseModule
import com.dantrap.cinemania.fintech.core.domain.di.DomainModule
import com.dantrap.cinemania.fintech.core.network.di.NetworkModule
import com.dantrap.cinemania.fintech.di.AppModule
import com.dantrap.cinemania.fintech.feature.details.di.DetailsModule
import com.dantrap.cinemania.fintech.feature.favorite.di.FavoriteModule
import com.dantrap.cinemania.fintech.feature.home.di.HomeModule
import com.dantrap.cinemania.fintech.feature.search.di.SearchModule
import com.dantrap.cinemania.fintech.feature.settings.di.SettingsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CinemaniaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CinemaniaApplication)
            androidLogger()
            modules(
                AppModule.module,
                DispatchersModule.module,
                DomainModule.module,
                DataModule.module,
                NetworkModule.module,
                HomeModule.module,
                SettingsModule.module,
                DetailsModule.module,
                FavoriteModule.module,
                DatabaseModule.module,
                SearchModule.module
            )
        }
    }
}
