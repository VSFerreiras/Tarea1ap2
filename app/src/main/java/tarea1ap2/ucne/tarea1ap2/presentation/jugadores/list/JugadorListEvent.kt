package tarea1ap2.ucne.tarea1ap2.presentation.jugadores.list

sealed interface JugadorListUiEvent {
    data object Load : JugadorListUiEvent
    data class Delete(val id: Int) : JugadorListUiEvent
    data class Edit(val id: Int) : JugadorListUiEvent
    data object AddNew : JugadorListUiEvent
    data object ViewPartidas : JugadorListUiEvent
}
