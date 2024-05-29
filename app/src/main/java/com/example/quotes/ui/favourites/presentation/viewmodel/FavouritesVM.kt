package com.example.quotes.ui.favourites.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotes.ui.favourites.data.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesVM @Inject constructor(
    private val repository: FavouritesRepository
): ViewModel() {

    private var _state = MutableStateFlow(FavouriteScreenState())
    val state = _state.asStateFlow()

    private var _channel = Channel<Boolean>()
    val channel = _channel.receiveAsFlow()

    init {
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            repository.getFavouritesOrderById().collectLatest { result ->
                _state.update {
                    it.copy(
                        favourites = result,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onEvent(event: FavouritesEvent){
        when(event){
            is FavouritesEvent.DeleteFavourite -> {
                viewModelScope.launch {
                    repository.deleteFavourites(event.favourite)
                    _channel.send(true)
                }
            }
            is FavouritesEvent.SaveFavourite -> {
                viewModelScope.launch {
                    repository.upsertFavourites(event.favourite)
                    _channel.send(true)
                }
            }
        }
    }

}