package com.androidtask.scratch.ui.screens.cardroot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidtask.scratch.domain.usecase.GetCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CardRootViewModel @Inject constructor(
    getCardUseCase: GetCardUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CardRootState())
    val uiState: StateFlow<CardRootState> get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCardUseCase().collect { card ->
                _uiState.emit(
                    _uiState.value.copy(
                        cardUUID = card.id,
                        activated = card.isActivated
                    )
                )
            }
        }
    }
}
