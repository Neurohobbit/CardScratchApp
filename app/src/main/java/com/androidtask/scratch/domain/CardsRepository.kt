package com.androidtask.scratch.domain

import com.androidtask.scratch.data.model.ActivationResponse
import com.androidtask.scratch.data.model.CardModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CardsRepository {
    fun getCardFlow(): StateFlow<CardModel>
    suspend fun scratchCard()
    suspend fun activateCode(): Flow<Result<ActivationResponse>>
}
