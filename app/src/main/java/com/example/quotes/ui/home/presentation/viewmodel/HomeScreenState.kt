package com.example.quotes.ui.home.presentation.viewmodel

import com.example.quotes.ui.home.data.model.Advice

data class HomeScreenState(
    val isLoading: Boolean = false,
    val adviceData: Advice = Advice(),
    val errorState: Boolean = false
)
