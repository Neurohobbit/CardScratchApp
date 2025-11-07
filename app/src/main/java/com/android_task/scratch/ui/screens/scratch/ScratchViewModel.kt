package com.android_task.scratch.ui.screens.scratch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android_task.scratch.domain.usecase.GetCardUseCase
import com.android_task.scratch.domain.usecase.ScratchCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScratchViewModel @Inject constructor(
    private val scratchCardUseCase: ScratchCardUseCase,
    private val getCardUseCase: GetCardUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScratchScreenState())
    val uiState: StateFlow<ScratchScreenState> get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCardUseCase().collect { card ->
                _uiState.emit(
                    _uiState.value.copy(
                        cardUUID = card.id,
                        isScratchingNow = card.isScratchingNow
                    )
                )
            }
        }
    }

    fun scratchCard() {
        viewModelScope.launch(Dispatchers.Default) {
            scratchCardUseCase()
        }
    }
}