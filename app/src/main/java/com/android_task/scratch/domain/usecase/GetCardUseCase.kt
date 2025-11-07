package com.android_task.scratch.domain.usecase

import com.android_task.scratch.domain.CardsRepository
import com.android_task.scratch.ui.model.Card
import com.android_task.scratch.ui.model.toCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCardUseCase @Inject constructor(
    private val repository: CardsRepository
) {
    operator fun invoke(): Flow<Card> {
        return repository.getCardFlow()
            .map { it.toCard() }
    }
}
