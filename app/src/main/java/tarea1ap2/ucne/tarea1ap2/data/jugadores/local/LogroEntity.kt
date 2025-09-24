package tarea1ap2.ucne.tarea1ap2.data.jugadores.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logros")
data class LogroEntity(
    @PrimaryKey(autoGenerate = true)
    val logroId: Int? = null,
    val titulo: String,
    val descripcion: String
)
