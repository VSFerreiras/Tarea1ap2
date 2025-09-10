package edu.ucne.tarea1ap2.data.tareas.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface JugadorDao {
    @Upsert
    suspend fun upsert(jugador: JugadorEntity)

    @Delete
    suspend fun delete(jugador: JugadorEntity)

    @Query("DELETE FROM Jugadores WHERE jugadorId = :id")
    suspend fun deleteById(id:Int)

    @Query("SELECT * FROM Jugadores ORDER BY jugadorId DESC")
    fun observeAll(): Flow<List<JugadorEntity>>

    @Query("SELECT * FROM jugadores WHERE Nombres = :nombre")
    suspend fun getByName(nombre: String): List<JugadorEntity>

    @Query("SELECT * FROM Jugadores WHERE jugadorId = :id")
    suspend fun getById(id: Int): JugadorEntity?
}