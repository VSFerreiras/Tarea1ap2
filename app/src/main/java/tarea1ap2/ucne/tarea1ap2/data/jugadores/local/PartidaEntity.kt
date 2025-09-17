package tarea1ap2.ucne.tarea1ap2.data.jugadores.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Partidas")
class PartidaEntity (
    @PrimaryKey(autoGenerate = true)
    val partidaId: Int = 0,
    val fecha: String = "",
    val jugador1Id: Int,
    val jugador2Id: Int,
    val ganadorId: Int? = null,
    val esFinalizada: Boolean = false
)
