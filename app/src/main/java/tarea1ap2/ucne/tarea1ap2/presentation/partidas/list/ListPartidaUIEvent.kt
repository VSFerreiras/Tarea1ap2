package tarea1ap2.ucne.tarea1ap2.presentation.partidas.list

import tarea1ap2.ucne.tarea1ap2.domain.model.Partida

sealed class ListPartidaUIEvent {
    data class Delete(val partida: Partida) : ListPartidaUIEvent()
    object Refresh : ListPartidaUIEvent()
    data class EditPartida(val id: Int) : ListPartidaUIEvent()
}