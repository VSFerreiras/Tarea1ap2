package edu.ucne.tarea1ap2.domain.usecase

import tarea1ap2.ucne.tarea1ap2.domain.repository.JugadorRepository
import tarea1ap2.ucne.tarea1ap2.domain.usecase.ValidationResult
import javax.inject.Inject

class ValidateJugadorUseCase @Inject constructor(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(nombres: String, currentJugadorId: Int? = null): ValidationResult {
        // Validación de longitud
        if (nombres.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "El nombre es obligatorio"
            )
        }

        if (nombres.length > 50) {
            return ValidationResult(
                successful = false,
                errorMessage = "El nombre no puede tener más de 50 caracteres"
            )
        }

        // Validación de caracteres (solo letras y espacios)
        if (!nombres.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$"))) {
            return ValidationResult(
                successful = false,
                errorMessage = "El nombre solo puede contener letras y espacios"
            )
        }

        // Validación de duplicados
        val jugadoresExistentes = repository.getJugadorByNombre(nombres)

        if (jugadoresExistentes.isNotEmpty()) {
            if (currentJugadorId == null) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "Ya existe un jugador con este nombre"
                )
            } else {
                val esDuplicadoDeOtro = jugadoresExistentes.any {
                    it.jugadorId != currentJugadorId
                }
                if (esDuplicadoDeOtro) {
                    return ValidationResult(
                        successful = false,
                        errorMessage = "Ya existe un jugador con este nombre"
                    )
                }
            }
        }

        return ValidationResult(successful = true)
    }
}