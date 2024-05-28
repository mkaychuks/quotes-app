package com.example.quotes.ui.favourites.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.quotes.ui.favourites.data.Favourites

data class FavouriteScreenState(
    val isLoading: Boolean = false,
    val favourites: List<Favourites> = emptyList(),
    val title: MutableState<String> = mutableStateOf("")
)
