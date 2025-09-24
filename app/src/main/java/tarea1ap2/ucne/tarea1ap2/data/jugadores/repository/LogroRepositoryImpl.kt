package tarea1ap2.ucne.tarea1ap2.data.jugadores.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tarea1ap2.ucne.tarea1ap2.data.jugadores.local.LogroDao
import tarea1ap2.ucne.tarea1ap2.data.jugadores.local.LogroEntity
import tarea1ap2.ucne.tarea1ap2.domain.model.Logro
import tarea1ap2.ucne.tarea1ap2.domain.repository.LogroRepository

class LogroRepositoryImpl(
    private val dao: LogroDao
) : LogroRepository {
    override fun observeLogros(): Flow<List<Logro>> {
        return dao.observeLogros().map { it.map { e -> e.toDomain() } }
    }

    override suspend fun getLogro(id: Int): Logro? {
        return dao.getLogro(id)?.toDomain()
    }

    override suspend fun upsert(logro: Logro): Int {
        return dao.upsert(logro.toEntity()).toInt()
    }

    override suspend fun delete(id: Int) {
        dao.getLogro(id)?.let { dao.delete(it) }
    }
}

fun LogroEntity.toDomain() = Logro(logroId, titulo, descripcion)
fun Logro.toEntity() = LogroEntity(logroId, titulo, descripcion)
