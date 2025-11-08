package com.androidtask.scratch.ui.screens.scratch

import java.util.UUID

data class ScratchScreenState(
    val cardUUID: UUID? = null,
    val isScratchingNow: Boolean = false
)
