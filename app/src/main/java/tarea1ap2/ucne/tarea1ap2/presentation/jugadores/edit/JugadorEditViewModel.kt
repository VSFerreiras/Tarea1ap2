package tarea1ap2.ucne.tarea1ap2.presentation.jugadores.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.tarea1ap2.domain.usecase.ValidateJugadorUseCase
import tarea1ap2.ucne.tarea1ap2.domain.model.Jugador
import tarea1ap2.ucne.tarea1ap2.domain.usecase.DeleteJugadorUseCase
import tarea1ap2.ucne.tarea1ap2.domain.usecase.GetJugadorUseCase
import tarea1ap2.ucne.tarea1ap2.domain.usecase.UpsertJugadorUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JugadorEditViewModel @Inject constructor(
    private val getJugadorUseCase: GetJugadorUseCase,
    private val upsertJugadorUseCase: UpsertJugadorUseCase,
    private val deleteJugadorUseCase: DeleteJugadorUseCase,
    private val validateJugadorUseCase: ValidateJugadorUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(value = JugadorEditUiState())
    val state: StateFlow<JugadorEditUiState> = _state.asStateFlow()

    fun onEvent(event: JugadorEditUiEvent) {
        when (event) {
            is JugadorEditUiEvent.Load -> onLoad(id = event.id)
            is JugadorEditUiEvent.NombresChanged -> {
                _state.update {
                    it.copy(nombres = event.value, nombresError = null)
                }
            }
            is JugadorEditUiEvent.PartidasChanged -> {
                // Validar que sea numérico y dentro del rango
                val partidasStr = event.value
                if (partidasStr.isBlank()) {
                    _state.update {
                        it.copy(partidas = 0, partidasError = null)
                    }
                    return
                }

                if (!partidasStr.matches(Regex("^\\d*$"))) {
                    _state.update {
                        it.copy(partidasError = "Solo se permiten números")
                    }
                    return
                }

                val partidas = partidasStr.toIntOrNull() ?: 0
                if (partidas > 100000) {
                    _state.update {
                        it.copy(partidasError = "Máximo 100,000 partidas")
                    }
                } else {
                    _state.update {
                        it.copy(partidas = partidas, partidasError = null)
                    }
                }
            }
            JugadorEditUiEvent.Save -> onSave()
            JugadorEditUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        _state.update {
            it.copy(
                jugadorId = id,
                isNew = id == null,
                nombres = if (id == null) "" else it.nombres,
                partidas = if (id == null) 0 else it.partidas
            )
        }

        id?.let { jugadorId ->
            viewModelScope.launch {
                getJugadorUseCase(jugadorId)?.let { jugador ->
                    _state.update {
                        it.copy(
                            nombres = jugador.nombres,
                            partidas = jugador.partidas
                        )
                    }
                }
            }
        }
    }

    private fun onSave() {
        val nombres = state.value.nombres.trim()
        val partidas = state.value.partidas

        // Validar partidas antes de guardar
        if (partidas > 100000) {
            _state.update {
                it.copy(partidasError = "Máximo 100,000 partidas")
            }
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isSaving = true,
                    nombresError = null,
                    partidasError = null
                )
            }

            try {
                val validationResult = validateJugadorUseCase(
                    nombres,
                    state.value.jugadorId
                )

                if (!validationResult.successful) {
                    _state.update {
                        it.copy(
                            isSaving = false,
                            nombresError = validationResult.errorMessage
                        )
                    }
                    return@launch
                }

                val jugador = Jugador(
                    jugadorId = state.value.jugadorId ?: 0,
                    nombres = nombres,
                    partidas = partidas
                )
                upsertJugadorUseCase(jugador)
                _state.update {
                    it.copy(isSaving = false, saved = true)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isSaving = false,
                        nombresError = "Error al guardar: ${e.message}"
                    )
                }
            }
        }
    }

    private fun onDelete() {
        state.value.jugadorId?.let { id ->
            viewModelScope.launch {
                _state.update { it.copy(isDeleting = true) }
                try {
                    deleteJugadorUseCase(id)
                    _state.update {
                        it.copy(isDeleting = false, deleted = true)
                    }
                } catch (e: Exception) {
                    _state.update { it.copy(isDeleting = false) }
                }
            }
        }
    }
}