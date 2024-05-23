package com.example.quotes.ui.search.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotes.ui.Result
import com.example.quotes.ui.search.domain.AdviceSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdviceSearchVM @Inject constructor(
    private val adviceSearchRepository: AdviceSearchRepository
): ViewModel() {

    private var _uiState = MutableStateFlow(AdviceSearchState())
    val uiState = _uiState.asStateFlow()

    init {
        searchForAdvice("spiders")
    }


    fun onEvent(event: AdviceSearchEvent){
        when(event){
            is AdviceSearchEvent.SearchButtonClicked -> {
                searchForAdvice(event.searchQuery)
            }
        }
    }


    private fun searchForAdvice(searchQuery: String){
        _uiState.update {it.copy(isLoading = true)}
        viewModelScope.launch {
            adviceSearchRepository.searchForAdvice(searchQuery).collectLatest { result ->
                when(result){
                    is Result.Error -> {
                        _uiState.update {it.copy(isLoading = false)}
                        Log.d("advice", "searchForAdvice: ERROR")
                    }
                    is Result.Success -> {
                        _uiState.update {it.copy(isLoading = false, idle = false, dataReceived = result.data!!)}
                        Log.d("advice", "searchForAdvice: ${result.data?.slips}")
                    }
                }
            }
        }
    }
}