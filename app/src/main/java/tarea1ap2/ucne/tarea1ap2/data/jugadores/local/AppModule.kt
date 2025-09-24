package tarea1ap2.ucne.tarea1ap2.data.jugadores.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tarea1ap2.ucne.tarea1ap2.data.jugadores.repository.LogroRepositoryImpl
import tarea1ap2.ucne.tarea1ap2.data.local.repository.JugadorRepositoryImpl
import tarea1ap2.ucne.tarea1ap2.domain.repository.LogroRepository
import tarea1ap2.ucne.tarea1ap2.domain.repository.JugadorRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            JugadorDataBase::class.java,
            "TareaApp.db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideJugadorDao(db: JugadorDataBase): JugadorDao = db.jugadorDao()

    @Provides
    @Singleton
    fun provideLogroDao(db: JugadorDataBase): LogroDao = db.logroDao()

    @Provides
    @Singleton
    fun provideJugadorRepository(dao: JugadorDao): JugadorRepository {
        return JugadorRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideLogroRepository(dao: LogroDao): LogroRepository {
        return LogroRepositoryImpl(dao)
    }
}
