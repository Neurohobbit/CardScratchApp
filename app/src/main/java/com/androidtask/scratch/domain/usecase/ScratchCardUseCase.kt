package com.androidtask.scratch.domain.usecase

import com.androidtask.scratch.domain.CardsRepository
import javax.inject.Inject

class ScratchCardUseCase @Inject constructor(
    private val repository: CardsRepository
) {
    suspend operator fun invoke() {
        repository.scratchCard()
    }
}
