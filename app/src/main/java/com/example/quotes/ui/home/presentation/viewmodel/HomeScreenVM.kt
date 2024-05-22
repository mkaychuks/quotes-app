package com.example.quotes.ui.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotes.ui.home.Result
import com.example.quotes.ui.home.domain.AdviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val adviceRepository: AdviceRepository,
) : ViewModel() {

    private var _homeScreenUiState = MutableStateFlow(HomeScreenState())
    val homeScreenUiState = _homeScreenUiState.asStateFlow()

    init {
        getDefaultAdvice()
    }

    private fun getDefaultAdvice() {
        _homeScreenUiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            adviceRepository.getRandomAdvice().collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _homeScreenUiState.update { it.copy(isLoading = false) }
                    }
                    is Result.Success -> {
                        _homeScreenUiState.update {
                            it.copy(
                                isLoading = false,
                                adviceData = result.data!!
                            )
                        }
                    }
                }
            }
        }
    }
}