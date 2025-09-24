package tarea1ap2.ucne.tarea1ap2.domain.repository

import kotlinx.coroutines.flow.Flow
import tarea1ap2.ucne.tarea1ap2.domain.model.Logro

interface LogroRepository {
    fun observeLogros(): Flow<List<Logro>>
    suspend fun getLogro(id: Int): Logro?
    suspend fun upsert(logro: Logro): Int
    suspend fun delete(id: Int)
}
