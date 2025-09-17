package tarea1ap2.ucne.tarea1ap2.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tarea1ap2.ucne.tarea1ap2.data.dao.PartidaDao
import tarea1ap2.ucne.tarea1ap2.data.jugadores.local.JugadorDao
import tarea1ap2.ucne.tarea1ap2.data.local.AppDatabase

import tarea1ap2.ucne.tarea1ap2.data.local.repository.JugadorRepositoryImpl
import tarea1ap2.ucne.tarea1ap2.data.repository.PartidaRepositoryImpl
import tarea1ap2.ucne.tarea1ap2.domain.repository.JugadorRepository
import tarea1ap2.ucne.tarea1ap2.domain.repository.PartidaRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideJugadorDao(db: AppDatabase): JugadorDao = db.jugadorDao()

    @Provides
    @Singleton
    fun providePartidaDao(db: AppDatabase): PartidaDao = db.partidaDao()

    @Provides
    @Singleton
    fun provideJugadorRepository(dao: JugadorDao): JugadorRepository = JugadorRepositoryImpl(dao)

    @Provides
    @Singleton
    fun providePartidaRepository(dao: PartidaDao): PartidaRepository = PartidaRepositoryImpl(dao)
}
