package tarea1ap2.ucne.tarea1ap2.presentation.jugadores.edit

data class JugadorEditUiState(
    val jugadorId: Int? = null,
    val nombres: String = "",
    val partidas: Int = 0,
    val nombresError: String? = null,
    val partidasError: String? = null,
    val isValid: Boolean = false,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false,
)