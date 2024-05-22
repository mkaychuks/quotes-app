package com.example.quotes.di

import com.example.quotes.ui.home.AdviceAPI
import com.example.quotes.ui.home.domain.AdviceRepository
import com.example.quotes.ui.home.domain.AdviceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // providing the api
    @Provides
    @Singleton
    fun provideApi(): AdviceAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AdviceAPI.BASE_URL)
            .build()
            .create(AdviceAPI::class.java)
    }

    // providing the repository
    @Provides
    @Singleton
    fun provideRepository(api: AdviceAPI): AdviceRepository{
        return AdviceRepositoryImpl(api)
    }
}