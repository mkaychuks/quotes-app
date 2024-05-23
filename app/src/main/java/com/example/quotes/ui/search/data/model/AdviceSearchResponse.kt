package com.example.quotes.ui.search.data.model

data class AdviceSearchResponse(
    val query: String,
    val slips: List<Slip>,
    val totalResults: String
)