package tarea1ap2.ucne.tarea1ap2.data.mappers

import tarea1ap2.ucne.tarea1ap2.data.jugadores.local.PartidaEntity
import tarea1ap2.ucne.tarea1ap2.domain.model.Partida

fun PartidaEntity.toPartida(): Partida {
    return Partida(
        partidaId = partidaId,
        fecha = fecha,
        jugador1Id = jugador1Id,
        jugador2Id = jugador2Id,
        ganadorId = ganadorId,
        esFinalizada = esFinalizada
    )
}

fun Partida.toEntity(): PartidaEntity {
    return PartidaEntity(
        partidaId = partidaId,
        fecha = fecha,
        jugador1Id = jugador1Id,
        jugador2Id = jugador2Id,
        ganadorId = ganadorId,
        esFinalizada = esFinalizada
    )
}