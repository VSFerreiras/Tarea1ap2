package tarea1ap2.ucne.tarea1ap2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import tarea1ap2.ucne.tarea1ap2.data.jugadores.local.PartidaEntity

@Dao
interface PartidaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(partida: PartidaEntity)

    @Update
    suspend fun update(partida: PartidaEntity)

    @Delete
    suspend fun delete(partida: PartidaEntity)

    @Query("SELECT * FROM Partidas ORDER BY fecha DESC")
    fun getAll(): Flow<List<PartidaEntity>>

    @Query("SELECT * FROM Partidas WHERE partidaId = :id")
    suspend fun getById(id: Int): PartidaEntity?

    @Query("SELECT * FROM Partidas WHERE jugador1Id = :jugadorId OR jugador2Id = :jugadorId ORDER BY fecha DESC")
    fun getByJugadorId(jugadorId: Int): Flow<List<PartidaEntity>>
}