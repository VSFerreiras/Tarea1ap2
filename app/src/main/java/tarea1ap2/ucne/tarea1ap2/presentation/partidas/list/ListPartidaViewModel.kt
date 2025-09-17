package tarea1ap2.ucne.tarea1ap2.presentation.partidas.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tarea1ap2.ucne.tarea1ap2.domain.model.Partida
import tarea1ap2.ucne.tarea1ap2.domain.usecase.DeletePartidaUseCase
import tarea1ap2.ucne.tarea1ap2.domain.usecase.GetPartidasUseCase
import tarea1ap2.ucne.tarea1ap2.presentation.jugadores.list.JugadorListUiEvent
import javax.inject.Inject

@HiltViewModel
class ListPartidaViewModel @Inject constructor(
    private val getPartidasUseCase: GetPartidasUseCase,
    private val deletePartidaUseCase: DeletePartidaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListPartidaUiState())
    val state: StateFlow<ListPartidaUiState> = _state.asStateFlow()

    private val _navigateToEdit = MutableStateFlow<Int?>(null)
    val navigateToEdit: StateFlow<Int?> = _navigateToEdit.asStateFlow()

    init {
        loadPartidas()
    }

    fun onEvent(event: ListPartidaUIEvent) {
        when (event) {
            is ListPartidaUIEvent.Delete -> deletePartida(event.partida)
            ListPartidaUIEvent.Refresh -> loadPartidas()
            is ListPartidaUIEvent.EditPartida -> {
                _navigateToEdit.value = event.id
            }
        }
    }

    private fun loadPartidas() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                getPartidasUseCase().collectLatest { partidas ->
                    _state.update { it.copy(partidas = partidas, isLoading = false) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    private fun deletePartida(partida: Partida) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                deletePartidaUseCase(partida)
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
    fun onNavigationComplete() {
        _navigateToEdit.value = null
    }
}