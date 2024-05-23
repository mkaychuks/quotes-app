package com.example.quotes.ui.search.domain

import com.example.quotes.ui.Result
import com.example.quotes.ui.search.data.model.AdviceSearchResponse
import kotlinx.coroutines.flow.Flow

interface AdviceSearchRepository {
    suspend fun searchForAdvice(searchQuery: String): Flow<Result<AdviceSearchResponse>>
}