package tarea1ap2.ucne.tarea1ap2.presentation.partidas.edit

import tarea1ap2.ucne.tarea1ap2.domain.model.Jugador

data class EditPartidaUiState(
    val partidaId: Int? = null,
    val jugadorXId: Int = 0,
    val jugadorOId: Int = 0,
    val ganadorId: Int? = null,
    val esFinalizada: Boolean = false,
    val board: List<Player?> = List(9) { null },
    val currentPlayer: Player = Player.X,
    val winner: Player? = null,
    val isDraw: Boolean = false,
    val gameStarted: Boolean = false,
    val jugadores: List<Jugador> = emptyList(),
    val isSaving: Boolean = false,
    val saved: Boolean = false,
    val error: String? = null
)

enum class Player(val symbol: String) {
    X("X"),
    O("O")
}