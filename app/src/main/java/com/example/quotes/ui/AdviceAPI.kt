package com.example.quotes.ui

import com.example.quotes.ui.home.data.model.Advice
import com.example.quotes.ui.search.data.model.AdviceSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AdviceAPI {

    // fetches a random advice from the api endpoint
    @GET("advice")
    suspend fun getRandomAdvice() : Advice

    // search for advice based on a query
    @GET("advice/search/{query}")
    suspend fun searchForAdvice(
        @Path("query") searchQuery: String
    ) : AdviceSearchResponse

    companion object {
        const val BASE_URL = "https://api.adviceslip.com/"
    }
}