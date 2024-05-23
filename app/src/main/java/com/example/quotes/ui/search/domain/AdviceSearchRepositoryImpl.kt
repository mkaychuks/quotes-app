package com.example.quotes.ui.search.domain

import com.example.quotes.ui.AdviceAPI
import com.example.quotes.ui.Result
import com.example.quotes.ui.search.data.model.AdviceSearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AdviceSearchRepositoryImpl @Inject constructor(
    private val api: AdviceAPI
) : AdviceSearchRepository {
    override suspend fun searchForAdvice(searchQuery: String): Flow<Result<AdviceSearchResponse>> {
        return flow {
            val adviceSearchResultFromApi = try {
                api.searchForAdvice(searchQuery = searchQuery)
            } catch (e: Exception){
                emit(Result.Error(message = e.message.toString()))
                return@flow
            }
            emit(Result.Success(data = adviceSearchResultFromApi))
        }
    }
}