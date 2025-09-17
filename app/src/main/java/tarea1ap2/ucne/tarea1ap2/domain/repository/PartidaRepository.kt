package tarea1ap2.ucne.tarea1ap2.domain.repository

import kotlinx.coroutines.flow.Flow
import tarea1ap2.ucne.tarea1ap2.domain.model.Partida

interface PartidaRepository {
    suspend fun insert(partida: Partida)
    suspend fun update(partida: Partida)
    suspend fun delete(partida: Partida)
    fun getAll(): Flow<List<Partida>>
    suspend fun getById(id: Int): Partida?
    fun getByJugadorId(jugadorId: Int): Flow<List<Partida>>
}