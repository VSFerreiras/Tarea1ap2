package tarea1ap2.ucne.tarea1ap2.data.jugadores.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LogroDao {
    @Query("SELECT * FROM logros")
    fun observeLogros(): Flow<List<LogroEntity>>

    @Query("SELECT * FROM logros WHERE logroId = :id")
    suspend fun getLogro(id: Int): LogroEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(logro: LogroEntity): Long

    @Delete
    suspend fun delete(logro: LogroEntity)
}
