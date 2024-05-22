package com.example.quotes.ui.home

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

    val api: AdviceAPI = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(AdviceAPI.BASE_URL)
        .build()
        .create(AdviceAPI::class.java)
}