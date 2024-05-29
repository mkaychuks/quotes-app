package com.example.quotes.ui.favourites.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {
    @Upsert
    suspend fun upsertFavourites(favourites: Favourites)

    @Delete
    suspend fun deleteFavourites(favourites: Favourites)


    @Query("SELECT * FROM favourites ORDER BY id ASC")
    fun getFavouritesOrderById(): Flow<List<Favourites>>
}