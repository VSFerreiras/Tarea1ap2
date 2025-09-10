package tarea1ap2.ucne.tarea1ap2.domain.repository

import tarea1ap2.ucne.tarea1ap2.domain.model.Jugador
import kotlinx.coroutines.flow.Flow

interface JugadorRepository {
    fun observeJugador(): Flow<List<Jugador>>
    suspend fun getJugador(id: Int): Jugador?
    suspend fun upsert(jugador: Jugador): Int
    suspend fun delete(id: Int)
    suspend fun getJugadorByNombre(nombre: String): List<Jugador>
}