package com.android_task.scratch.domain

import com.android_task.scratch.data.model.ActivationResponse
import com.android_task.scratch.data.model.CardModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CardsRepository {
    fun getCardFlow(): StateFlow<CardModel>
    suspend fun scratchCard()
    suspend fun activateCode(): Flow<Result<ActivationResponse>>
}
