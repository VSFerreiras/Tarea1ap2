package tarea1ap2.ucne.tarea1ap2.data.jugadores.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [JugadorEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class JugadorDataBase : RoomDatabase() {
    public abstract fun jugadorDao(): JugadorDao
}