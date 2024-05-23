package com.example.quotes.ui.search.data.model

data class AdviceSearchResponse(
    val query: String? = null,
    val slips: List<Slip>? = null,
    val totalResults: String? = null
)