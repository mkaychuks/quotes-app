package com.example.quotes.ui.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotes.ui.home.Result
import com.example.quotes.ui.home.domain.AdviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val adviceRepository: AdviceRepository
) : ViewModel(){

    init {
        getDefaultAdvice()
    }

    private fun getDefaultAdvice(){
        viewModelScope.launch {
            adviceRepository.getRandomAdvice().collectLatest { result ->
                when(result){
                    is Result.Error -> {
                        Log.d("Advice", "getDefaultAdvice: Failure for advice")
                    }
                    is Result.Success -> {
                        Log.d("Advice", "getDefaultAdvice: ${result.data?.slip}")
                    }
                }
            }
        }
    }
}