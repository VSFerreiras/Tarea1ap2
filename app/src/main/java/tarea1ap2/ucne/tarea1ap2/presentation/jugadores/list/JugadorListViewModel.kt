package edu.ucne.tarea1ap2.presentation.jugadores.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tarea1ap2.ucne.tarea1ap2.domain.usecase.DeleteJugadorUseCase
import tarea1ap2.ucne.tarea1ap2.domain.usecase.ObserveJugadorUseCase
import tarea1ap2.ucne.tarea1ap2.presentation.jugadores.list.JugadorListUiEvent
import tarea1ap2.ucne.tarea1ap2.presentation.jugadores.list.JugadorListUiState
import javax.inject.Inject

@HiltViewModel
class JugadorListViewModel @Inject constructor(
    private val observeJugadorUseCase: ObserveJugadorUseCase,
    private val deleteJugadorUseCase: DeleteJugadorUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(value = JugadorListUiState())
    val state: StateFlow<JugadorListUiState> = _state.asStateFlow()

    private val _navigateToEdit = MutableStateFlow<Int?>(null)
    val navigateToEdit: StateFlow<Int?> = _navigateToEdit.asStateFlow()

    init {
        loadJugadores()
    }

    fun onEvent(event: JugadorListUiEvent) {
        when (event) {
            JugadorListUiEvent.Load -> loadJugadores()
            is JugadorListUiEvent.Delete -> onDelete(event.id)
            is JugadorListUiEvent.Edit -> onEdit(event.id)
            JugadorListUiEvent.AddNew -> onAddNew()
        }
    }

    private fun loadJugadores() {
        viewModelScope.launch {
            observeJugadorUseCase().collect { jugadores ->
                _state.update { it.copy(jugadores = jugadores, isLoading = false) }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                deleteJugadorUseCase(id)
                // El Flow se actualizará automáticamente con la nueva lista
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error al eliminar: ${e.message}"
                    )
                }
            }
        }
    }

    private fun onEdit(id: Int) {
        _navigateToEdit.value = id
    }

    private fun onAddNew() {
        _navigateToEdit.value = -1
    }

    fun onNavigationComplete() {
        _navigateToEdit.value = null
    }
}