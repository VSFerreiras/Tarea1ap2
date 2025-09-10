package tarea1ap2.ucne.tarea1ap2.domain.usecase

import tarea1ap2.ucne.tarea1ap2.domain.repository.JugadorRepository
import javax.inject.Inject

class DeleteJugadorUseCase @Inject constructor(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.delete(id)
    }
}