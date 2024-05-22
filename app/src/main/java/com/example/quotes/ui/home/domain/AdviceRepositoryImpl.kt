package com.example.quotes.ui.home.domain

import com.example.quotes.ui.home.AdviceAPI
import com.example.quotes.ui.home.Result
import com.example.quotes.ui.home.data.model.Advice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AdviceRepositoryImpl @Inject constructor(
    private val api: AdviceAPI
) : AdviceRepository {
    override suspend fun getRandomAdvice(): Flow<Result<Advice>> {
        return flow {
            val adviceFromApi = try {
                api.getRandomAdvice()
            } catch (e: Exception){
                emit(Result.Error(message = e.message))
                return@flow
            }
            emit(Result.Success(data = adviceFromApi))
        }
    }
}