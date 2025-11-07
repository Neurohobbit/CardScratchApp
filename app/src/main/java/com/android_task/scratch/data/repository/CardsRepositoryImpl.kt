package com.android_task.scratch.data.repository

import com.android_task.scratch.data.datasource.remote.ApiService
import com.android_task.scratch.data.model.CardModel
import com.android_task.scratch.domain.CardsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class CardsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CardsRepository {

    private val _card = MutableStateFlow(CardModel())
    val card: StateFlow<CardModel> = _card.asStateFlow()

    override fun getCardFlow(): StateFlow<CardModel> = card

    override suspend fun scratchCard() {
        try {
            changeScratchProcessStatus(isScratching = true)
            delay(2000L)
            _card.emit(
                _card.value.copy(
                    id = UUID.randomUUID()
                )
            )
            changeScratchProcessStatus(isScratching = false)
        } catch (e: CancellationException) {
            changeScratchProcessStatus(isScratching = false)
        }
    }

    override suspend fun activateCode() = flow {
        val id = _card.value.id ?: return@flow emit(Result.failure(Exception("Card ID is null")))

        emit(
            runCatching {
                val response = apiService.activateCode(id.toString())
                val body = response.body()
                    ?: throw Exception("Empty response body (code: ${response.code()})")
                if (!response.isSuccessful) throw Exception("Server error: ${response.code()}")

                _card.emit(
                    _card.value.copy(
                        isActivated = true
                    )
                )

                body
            }
        )
    }.flowOn(Dispatchers.IO)

    private suspend fun changeScratchProcessStatus(isScratching: Boolean) {
        _card.emit(
            _card.value.copy(
                isScratchingNow = isScratching
            )
        )
    }
}