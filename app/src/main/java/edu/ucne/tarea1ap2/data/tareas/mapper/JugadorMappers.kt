package edu.ucne.tarea1ap2.data.tareas.mapper

import edu.ucne.tarea1ap2.domain.model.Jugador
import edu.ucne.tarea1ap2.data.tareas.local.JugadorEntity


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