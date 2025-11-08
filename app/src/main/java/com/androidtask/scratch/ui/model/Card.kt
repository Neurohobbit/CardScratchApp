package com.androidtask.scratch.ui.model

import com.androidtask.scratch.data.model.CardModel
import java.util.UUID

data class Card(
    val id: UUID? = null,
    val isActivated: Boolean = false,
    val isScratchingNow: Boolean = false
)

fun CardModel.toCard() = Card(
    id = id,
    isActivated = isActivated,
    isScratchingNow = isScratchingNow
)
