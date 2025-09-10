package edu.ucne.tarea1ap2.data.local.repository

import edu.ucne.tarea1ap2.data.local.*
import edu.ucne.tarea1ap2.data.tareas.local.JugadorDao
import edu.ucne.tarea1ap2.domain.model.Jugador
import edu.ucne.tarea1ap2.domain.repository.JugadorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class JugadorRepositoryImpl @Inject constructor(
    private val jugadorDao: JugadorDao
) : JugadorRepository {

    override suspend fun upsert(jugador: Jugador) {
        jugadorDao.upsert(jugador.toEntity())
    }

    override suspend fun delete(jugador: Jugador) {
        jugadorDao.delete(jugador.toEntity())
    }

    override suspend fun deleteById(id: Int) {
        jugadorDao.deleteById(id)
    }

    override fun observeAll(): Flow<List<Jugador>> {
        return jugadorDao.observeAll().map { entities ->
            entities.map { it.toJugador() }
        }
    }

    override suspend fun getByNombre(nombre: String): List<Jugador> {
        return jugadorDao.getByName(nombre).map { it.toJugador() }
    }

    override suspend fun getById(id: Int): Jugador? {
        return jugadorDao.getById(id)?.toJugador()
    }

    override suspend fun existsByNombre(nombre: String): Boolean {
        return jugadorDao.getByName(nombre).isNotEmpty()
    }
}