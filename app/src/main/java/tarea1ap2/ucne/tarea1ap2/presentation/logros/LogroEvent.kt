package tarea1ap2.ucne.tarea1ap2.presentation.logros

sealed class LogroEvent {
    data class Load(val id: Int?) : LogroEvent()
    data class Save(val titulo: String, val descripcion: String) : LogroEvent()
    data class Delete(val id: Int) : LogroEvent()
    object New : LogroEvent()
}
