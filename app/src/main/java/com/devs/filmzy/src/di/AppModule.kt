package com.devs.filmzy.src.di

import com.devs.filmzy.src.services.ApiInterface
import com.devs.filmzy.src.services.RetrofitInstance
import com.devs.filmzy.src.utils.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNavigationManager(): NavigationManager {
        return NavigationManager()
    }

    @Provides
    @Singleton
    fun provideApiInterface(): ApiInterface {
        return RetrofitInstance.api
    }
}
