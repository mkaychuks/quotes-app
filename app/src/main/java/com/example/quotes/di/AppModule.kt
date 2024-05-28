package com.example.quotes.di

import android.app.Application
import androidx.room.Room
import com.example.quotes.ui.AdviceAPI
import com.example.quotes.ui.favourites.data.FavouritesDatabase
import com.example.quotes.ui.favourites.data.FavouritesRepository
import com.example.quotes.ui.favourites.data.FavouritesRepositoryImpl
import com.example.quotes.ui.home.domain.AdviceRepository
import com.example.quotes.ui.home.domain.AdviceRepositoryImpl
import com.example.quotes.ui.search.domain.AdviceSearchRepository
import com.example.quotes.ui.search.domain.AdviceSearchRepositoryImpl
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

    @Provides
    @Singleton
    fun provideSearchRepository(api: AdviceAPI): AdviceSearchRepository{
        return AdviceSearchRepositoryImpl(api)
    }

    // providing the room database
    @Provides
    @Singleton
    fun provideFavouriteDatabase(app: Application): FavouritesDatabase {
        return Room.databaseBuilder(
            app,
            FavouritesDatabase::class.java,
            "favourites"
        ).build()
    }

    // providing the favourites repository
    @Provides
    @Singleton
    fun provideFavouriteRepository(db: FavouritesDatabase): FavouritesRepository {
       return FavouritesRepositoryImpl(db.dao)
    }
}