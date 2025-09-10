package tarea1ap2.ucne.tarea1ap2.data.jugadores.mapper

import tarea1ap2.ucne.tarea1ap2.domain.model.Jugador
import tarea1ap2.ucne.tarea1ap2.data.jugadores.local.JugadorEntity


fun JugadorEntity.toDomain(): Jugador = Jugador(
    jugadorId = jugadorId,
    nombres = nombres,
    partidas = partidas,

    )
fun Jugador.toEntity(): JugadorEntity = JugadorEntity(
    jugadorId = jugadorId,
    nombres = nombres,
    partidas = partidas,
)