package com.android_task.scratch.ui.screens.activation

import java.util.UUID

data class ActivationState(
    val cardUUID: UUID? = null,
    val isActivatingNow: Boolean = false,
    val isActive: Boolean = false,
    val error: String? = null
) {
    val activationButtonEnabled: Boolean
        get() = !isActivatingNow && !isActive
}
