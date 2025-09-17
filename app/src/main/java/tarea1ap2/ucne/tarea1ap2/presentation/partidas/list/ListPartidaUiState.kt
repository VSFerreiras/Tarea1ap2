package tarea1ap2.ucne.tarea1ap2.presentation.partidas.list

import tarea1ap2.ucne.tarea1ap2.domain.model.Partida

data class ListPartidaUiState(
    val partidas: List<Partida> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)