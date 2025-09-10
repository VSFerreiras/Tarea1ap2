package tarea1ap2.ucne.tarea1ap2.data.local.repository

import tarea1ap2.ucne.tarea1ap2.data.jugadores.local.JugadorDao
import tarea1ap2.ucne.tarea1ap2.data.jugadores.mapper.toDomain
import tarea1ap2.ucne.tarea1ap2.data.jugadores.mapper.toEntity
import tarea1ap2.ucne.tarea1ap2.domain.model.Jugador
import tarea1ap2.ucne.tarea1ap2.domain.repository.JugadorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class JugadorRepositoryImpl @Inject constructor(
    private val jugadorDao: JugadorDao
) : JugadorRepository {

    override fun observeJugador(): Flow<List<Jugador>> {
        return jugadorDao.observeAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getJugador(id: Int): Jugador? {
        return jugadorDao.getById(id)?.toDomain()
    }

    override suspend fun upsert(jugador: Jugador): Int {
        jugadorDao.upsert(jugador.toEntity())
        val jugadorEntity = jugadorDao.getByName(jugador.nombres).firstOrNull()
        return jugadorEntity?.jugadorId ?: -1
    }

    override suspend fun delete(id: Int) {
        jugadorDao.deleteById(id)
    }

    override suspend fun getJugadorByNombre(nombre: String): List<Jugador> {
        return jugadorDao.getByName(nombre).map { it.toDomain() }
    }
}