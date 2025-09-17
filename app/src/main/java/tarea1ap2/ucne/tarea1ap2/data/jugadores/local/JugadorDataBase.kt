package tarea1ap2.ucne.tarea1ap2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import tarea1ap2.ucne.tarea1ap2.data.jugadores.local.JugadorDao
import tarea1ap2.ucne.tarea1ap2.data.jugadores.local.JugadorEntity
import tarea1ap2.ucne.tarea1ap2.data.dao.PartidaDao
import tarea1ap2.ucne.tarea1ap2.data.jugadores.local.PartidaEntity

@Database(
    entities = [JugadorEntity::class, PartidaEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jugadorDao(): JugadorDao
    abstract fun partidaDao(): PartidaDao
}
