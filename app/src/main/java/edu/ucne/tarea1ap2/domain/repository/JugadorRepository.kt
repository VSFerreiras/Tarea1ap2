package edu.ucne.tarea1ap2.domain.repository

import edu.ucne.tarea1ap2.domain.model.Jugador
import kotlinx.coroutines.flow.Flow

interface JugadorRepository {
    fun observeJugador(): Flow<List<Jugador>>
    suspend fun getJugador(id: Int): Jugador?
    suspend fun upsertJugador(jugador: Jugador): Int
    suspend fun deleteJugador(id: Int)
    suspend fun getJugadorByNombre(nombre: String): List<Jugador>
}