package com.androidtask.scratch.domain.usecase

import com.androidtask.scratch.domain.CardsRepository
import com.androidtask.scratch.ui.model.ActivationResult
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ActivateCardUseCase @Inject constructor(
    private val repository: CardsRepository
) {
    suspend operator fun invoke(): Flow<ActivationResult> =
        repository.activateCode()
            .map {
                ActivationResult(
                    isActivated = it.isSuccess,
                    error = if (it.exceptionOrNull() == null) {
                        null
                    } else {
                        it.exceptionOrNull()?.message
                    }
                )
            }
}
