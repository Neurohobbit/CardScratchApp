package com.android_task.scratch.ui.model

import com.android_task.scratch.data.model.CardModel
import java.util.UUID

data class Card(
    val id: UUID? = null,
    val isActivated: Boolean = false,
    val isScratchingNow:Boolean = false
)

fun CardModel.toCard() = Card(
    id = id,
    isActivated = isActivated,
    isScratchingNow = isScratchingNow
)
