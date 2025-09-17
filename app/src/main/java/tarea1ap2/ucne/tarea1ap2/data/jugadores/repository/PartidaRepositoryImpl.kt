package tarea1ap2.ucne.tarea1ap2.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tarea1ap2.ucne.tarea1ap2.data.dao.PartidaDao
import tarea1ap2.ucne.tarea1ap2.data.mappers.toEntity
import tarea1ap2.ucne.tarea1ap2.data.mappers.toPartida
import tarea1ap2.ucne.tarea1ap2.domain.model.Partida
import tarea1ap2.ucne.tarea1ap2.domain.repository.PartidaRepository
import javax.inject.Inject

class PartidaRepositoryImpl @Inject constructor(
    private val partidaDao: PartidaDao
) : PartidaRepository {

    override suspend fun insert(partida: Partida) = partidaDao.insert(partida.toEntity())

    override suspend fun update(partida: Partida) = partidaDao.update(partida.toEntity())

    override suspend fun delete(partida: Partida) = partidaDao.delete(partida.toEntity())

    override fun getAll(): Flow<List<Partida>> = partidaDao.getAll().map { entities ->
        entities.map { it.toPartida() }
    }

    override suspend fun getById(id: Int): Partida? = partidaDao.getById(id)?.toPartida()

    override fun getByJugadorId(jugadorId: Int): Flow<List<Partida>> =
        partidaDao.getByJugadorId(jugadorId).map { entities ->
            entities.map { it.toPartida() }
        }
}