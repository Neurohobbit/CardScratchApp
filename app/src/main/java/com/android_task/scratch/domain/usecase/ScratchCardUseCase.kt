package com.android_task.scratch.domain.usecase

import com.android_task.scratch.domain.CardsRepository
import javax.inject.Inject

class ScratchCardUseCase @Inject constructor(
    private val repository: CardsRepository
) {
    suspend operator fun invoke() {
        repository.scratchCard()
    }
}