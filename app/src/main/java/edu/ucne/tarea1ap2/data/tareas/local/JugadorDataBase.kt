package edu.ucne.tarea1ap2.data.tareas.local

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