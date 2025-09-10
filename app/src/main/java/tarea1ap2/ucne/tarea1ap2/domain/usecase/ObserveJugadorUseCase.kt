package tarea1ap2.ucne.tarea1ap2.domain.usecase

import tarea1ap2.ucne.tarea1ap2.domain.model.Jugador
import tarea1ap2.ucne.tarea1ap2.domain.repository.JugadorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveJugadorUseCase @Inject constructor(
    private val repository: JugadorRepository
) {
    operator fun invoke(): Flow<List<Jugador>> {
        return repository.observeJugador()
    }
}