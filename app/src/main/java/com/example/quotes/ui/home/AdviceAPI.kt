package com.example.quotes.ui.home

import com.example.quotes.ui.home.data.model.Advice
import retrofit2.http.GET

interface AdviceAPI {

    // fetches a random advice from the api endpoint
    @GET("advice")
    suspend fun getRandomAdvice() : Advice

    companion object {
        const val BASE_URL = "https://api.adviceslip.com/"
    }
}