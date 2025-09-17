package tarea1ap2.ucne.tarea1ap2.presentation.partidas.edit

sealed class EditPartidaUIEvent {
    data class Load(val id: Int?) : EditPartidaUIEvent()
    data class JugadorXSelected(val jugadorId: Int) : EditPartidaUIEvent()
    data class JugadorOSelected(val jugadorId: Int) : EditPartidaUIEvent()
    data class CellClick(val index: Int) : EditPartidaUIEvent()
    object StartGame : EditPartidaUIEvent()
    object RestartGame : EditPartidaUIEvent()
    object SaveGame : EditPartidaUIEvent()
}