package com.androidtask.scratch.data.repository

import com.androidtask.scratch.data.datasource.remote.ApiService
import com.androidtask.scratch.data.model.CardModel
import com.androidtask.scratch.domain.CardsRepository
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CardsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CardsRepository {

    private val _card = MutableStateFlow(CardModel())
    val card: StateFlow<CardModel> = _card.asStateFlow()

    override fun getCardFlow(): StateFlow<CardModel> = card

    @Suppress("SwallowedException")
    override suspend fun scratchCard() {
        try {
            changeScratchProcessStatus(isScratching = true)
            delay(SCRATCHING_DELAY)
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

    @Suppress("TooGenericExceptionThrown")
    override suspend fun activateCode() = flow {
        val id = _card.value.id ?: return@flow emit(Result.failure(Exception("Card ID is null")))

        emit(
            runCatching {
                val response = apiService.activateCode(id.toString())
                val body = response.body()
                    ?: throw Exception("Empty response body (code: ${response.code()})")
                if (!response.isSuccessful) throw Exception("Server error: ${response.code()}")

                val androidCode = body.android.toIntOrNull()
                if (androidCode != null && androidCode > ERROR_BORDER) {
                    throw Exception("Android code more than $ERROR_BORDER")
                } else {
                    _card.emit(
                        _card.value.copy(
                            isActivated = true
                        )
                    )
                    return@runCatching body
                }
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

    companion object {
        const val ERROR_BORDER = 277028
        const val SCRATCHING_DELAY = 2000L
    }
}
