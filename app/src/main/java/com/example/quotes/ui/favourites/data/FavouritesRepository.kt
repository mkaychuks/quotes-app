package com.example.quotes.ui.favourites.data

import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    suspend fun upsertFavourites(favourites: Favourites)

    suspend fun deleteFavourites(favourites: Favourites)

    fun getFavouritesOrderById(): Flow<List<Favourites>>
}