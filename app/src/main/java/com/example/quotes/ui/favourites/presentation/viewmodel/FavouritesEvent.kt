package com.example.quotes.ui.favourites.presentation.viewmodel

import com.example.quotes.ui.favourites.data.Favourites

sealed interface FavouritesEvent{
    data class DeleteFavourite(val favourite: Favourites) : FavouritesEvent
    data class SaveFavourite(val favourite: Favourites): FavouritesEvent
}
