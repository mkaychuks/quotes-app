package com.example.quotes.ui.search.presentation.viewmodel

import com.example.quotes.ui.search.data.model.AdviceSearchResponse

data class AdviceSearchState(
    val isLoading: Boolean = false,
    val dataReceived: AdviceSearchResponse = AdviceSearchResponse(),
    val idle: Boolean = true
)


sealed class AdviceSearchEvent{
    data class SearchButtonClicked(val searchQuery: String): AdviceSearchEvent()
}