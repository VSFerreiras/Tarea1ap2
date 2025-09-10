package tarea1ap2.ucne.tarea1ap2.presentation.jugadores.edit

sealed interface JugadorEditUiEvent {
    data class Load(val id: Int?) : JugadorEditUiEvent
    data class NombresChanged(val value: String) : JugadorEditUiEvent
    data class PartidasChanged(val value: String) : JugadorEditUiEvent
    data object Save : JugadorEditUiEvent
    data object Delete : JugadorEditUiEvent

}