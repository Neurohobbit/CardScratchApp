package com.android_task.scratch.domain.usecase

import com.android_task.scratch.domain.CardsRepository
import com.android_task.scratch.ui.model.ActivationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ActivateCardUseCase @Inject constructor(
    private val repository: CardsRepository
) {
    suspend operator fun invoke(): Flow<ActivationResult> =
        repository.activateCode()
            .map {
                ActivationResult(
                    isActivated = it.isSuccess,
                    error = if (it.exceptionOrNull() == null)
                        null
                    else
                        it.exceptionOrNull().toString()
                )
            }
}
