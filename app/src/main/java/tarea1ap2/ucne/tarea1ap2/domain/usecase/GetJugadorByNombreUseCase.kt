package tarea1ap2.ucne.tarea1ap2.domain.usecase

import tarea1ap2.ucne.tarea1ap2.domain.model.Jugador
import tarea1ap2.ucne.tarea1ap2.domain.repository.JugadorRepository
import javax.inject.Inject

class GetJugadorByNombreUseCase @Inject constructor(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(nombre: String): List<Jugador> {
        return repository.getJugadorByNombre(nombre)
    }
}