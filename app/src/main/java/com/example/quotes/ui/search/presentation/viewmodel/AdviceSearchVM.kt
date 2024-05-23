package com.example.quotes.ui.search.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotes.ui.Result
import com.example.quotes.ui.search.domain.AdviceSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdviceSearchVM @Inject constructor(
    private val adviceSearchRepository: AdviceSearchRepository
): ViewModel() {

    init {
      searchForAdvice("spiders")
    }


    private fun searchForAdvice(searchQuery: String){
        viewModelScope.launch {
            adviceSearchRepository.searchForAdvice(searchQuery).collectLatest { result ->
                when(result){
                    is Result.Error -> {
                        Log.d("advice", "searchForAdvice: ERROR")
                    }
                    is Result.Success -> {
                        Log.d("advice", "searchForAdvice: ${result.data?.slips}")
                    }
                }
            }
        }
    }



}