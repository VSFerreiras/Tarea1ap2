package tarea1ap2.ucne.tarea1ap2.domain.usecase

import tarea1ap2.ucne.tarea1ap2.domain.model.Jugador
import tarea1ap2.ucne.tarea1ap2.domain.repository.JugadorRepository
import javax.inject.Inject

class UpsertJugadorUseCase @Inject constructor(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(jugador: Jugador): Int {
        return repository.upsert(jugador)
    }
}