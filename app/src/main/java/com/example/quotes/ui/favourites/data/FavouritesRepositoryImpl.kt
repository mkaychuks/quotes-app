package com.example.quotes.ui.favourites.data

import kotlinx.coroutines.flow.Flow

class FavouritesRepositoryImpl(
    private val dao: FavouritesDao
): FavouritesRepository {
    override suspend fun upsertFavourites(favourites: Favourites) {
        dao.upsertFavourites(favourites)
    }

    override suspend fun deleteFavourites(favourites: Favourites) {
        dao.deleteFavourites(favourites)
    }

    override fun getFavouritesOrderById(): Flow<List<Favourites>> {
        return dao.getFavouritesOrderById()
    }
}