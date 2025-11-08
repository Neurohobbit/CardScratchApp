package com.androidtask.scratch.domain.usecase

import com.androidtask.scratch.domain.CardsRepository
import com.androidtask.scratch.ui.model.Card
import com.androidtask.scratch.ui.model.toCard
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCardUseCase @Inject constructor(
    private val repository: CardsRepository
) {
    operator fun invoke(): Flow<Card> {
        return repository.getCardFlow()
            .map { it.toCard() }
    }
}
