package com.example.quotes.ui.home.domain

import com.example.quotes.ui.Result
import com.example.quotes.ui.home.data.model.Advice
import kotlinx.coroutines.flow.Flow

interface AdviceRepository {

    // fetches a random advice from api
    suspend fun getRandomAdvice(): Flow<Result<Advice>>
}