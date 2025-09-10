package tarea1ap2.ucne.tarea1ap2.presentation.jugadores.list

import tarea1ap2.ucne.tarea1ap2.domain.model.Jugador

data class JugadorListUiState(
    val jugadores: List<Jugador> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)