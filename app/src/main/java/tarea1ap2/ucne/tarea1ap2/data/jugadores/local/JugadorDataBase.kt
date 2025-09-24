package tarea1ap2.ucne.tarea1ap2.data.jugadores.local

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [
        JugadorEntity::class,
        LogroEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class JugadorDataBase : RoomDatabase() {
    abstract fun jugadorDao(): JugadorDao
    abstract fun logroDao(): LogroDao
}
