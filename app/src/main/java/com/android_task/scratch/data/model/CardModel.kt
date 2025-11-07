package com.android_task.scratch.data.model

import java.util.UUID

data class CardModel(
    val id: UUID? = null,
    val isActivated: Boolean = false,
    val isScratchingNow: Boolean = false
)
