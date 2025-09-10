package tarea1ap2.ucne.tarea1ap2.domain.usecase

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)