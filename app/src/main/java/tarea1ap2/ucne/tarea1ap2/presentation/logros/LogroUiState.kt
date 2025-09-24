package tarea1ap2.ucne.tarea1ap2.presentation.logros

import tarea1ap2.ucne.tarea1ap2.domain.model.Logro

data class LogroUiState(
    val logros: List<Logro> = emptyList(),
    val titulo: String = "",
    val descripcion: String = "",
    val editingId: Int? = null,
    val isCreating: Boolean = false,
    val saved: Boolean = false,
    val deleted: Boolean = false
)