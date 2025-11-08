package com.androidtask.scratch.ui.screens.activation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidtask.scratch.domain.usecase.ActivateCardUseCase
import com.androidtask.scratch.domain.usecase.GetCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ActivationViewModel @Inject constructor(
    private val activateCardUseCase: ActivateCardUseCase,
    private val getCardUseCase: GetCardUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActivationState())
    val uiState: StateFlow<ActivationState> get() = _uiState.asStateFlow()

    private val _uiAction = MutableSharedFlow<ActivationAction>()
    val uiAction: SharedFlow<ActivationAction> get() = _uiAction.asSharedFlow()

    init {
        viewModelScope.launch {
            getCardUseCase().collect { card ->
                _uiState.emit(
                    _uiState.value.copy(
                        cardUUID = card.id,
                        isActive = card.isActivated
                    )
                )
            }
        }
    }

    fun activeCard() {
        viewModelScope.launch(Dispatchers.Default) {
            setActivationProcess(inProgress = true)
            activateCardUseCase().collect {
                setActivationProcess(inProgress = false)
                _uiState.emit(
                    _uiState.value.copy(
                        error = it.error
                    )
                )

                if (it.error != null) {
                    _uiAction.emit(ActivationAction.ShowActivationError(it.error))
                }
            }
        }
    }

    private fun setActivationProcess(inProgress: Boolean) {
        viewModelScope.launch {
            _uiState.emit(
                _uiState.value.copy(
                    isActivatingNow = inProgress
                )
            )
        }
    }
}
