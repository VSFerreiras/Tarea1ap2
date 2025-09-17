package tarea1ap2.ucne.tarea1ap2.domain.usecase

import kotlinx.coroutines.flow.Flow
import tarea1ap2.ucne.tarea1ap2.domain.model.Partida
import tarea1ap2.ucne.tarea1ap2.domain.repository.PartidaRepository
import javax.inject.Inject

class GetPartidasUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    operator fun invoke(): Flow<List<Partida>> = repository.getAll()
}

class GetPartidaByIdUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    suspend operator fun invoke(id: Int): Partida? = repository.getById(id)
}

class GetPartidasByJugadorUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    operator fun invoke(jugadorId: Int): Flow<List<Partida>> = repository.getByJugadorId(jugadorId)
}

class InsertPartidaUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    suspend operator fun invoke(partida: Partida) = repository.insert(partida)
}

class UpdatePartidaUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    suspend operator fun invoke(partida: Partida) = repository.update(partida)
}

class DeletePartidaUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    suspend operator fun invoke(partida: Partida) = repository.delete(partida)
}