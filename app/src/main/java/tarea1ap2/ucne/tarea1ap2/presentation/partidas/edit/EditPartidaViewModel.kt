package tarea1ap2.ucne.tarea1ap2.presentation.partidas.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tarea1ap2.ucne.tarea1ap2.domain.model.Partida
import tarea1ap2.ucne.tarea1ap2.domain.usecase.GetJugadorUseCase
import tarea1ap2.ucne.tarea1ap2.domain.usecase.GetPartidaByIdUseCase
import tarea1ap2.ucne.tarea1ap2.domain.usecase.InsertPartidaUseCase
import tarea1ap2.ucne.tarea1ap2.domain.usecase.UpdatePartidaUseCase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import tarea1ap2.ucne.tarea1ap2.presentation.partidas.edit.Player

@HiltViewModel
class EditPartidaViewModel @Inject constructor(
    private val getPartidaByIdUseCase: GetPartidaByIdUseCase,
    private val getJugadorUseCase: GetJugadorUseCase,
    private val insertPartidaUseCase: InsertPartidaUseCase,
    private val updatePartidaUseCase: UpdatePartidaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditPartidaUiState())
    val state: StateFlow<EditPartidaUiState> = _state.asStateFlow()

    private val _navigateToEdit = MutableStateFlow<Int?>(null)
    val navigateToEdit: StateFlow<Int?> = _navigateToEdit.asStateFlow()

    fun onEvent(event: EditPartidaUIEvent) {
        when (event) {
            is EditPartidaUIEvent.Load -> loadPartida(event.id)
            is EditPartidaUIEvent.JugadorXSelected -> selectJugadorX(event.jugadorId)
            is EditPartidaUIEvent.JugadorOSelected -> selectJugadorO(event.jugadorId)
            is EditPartidaUIEvent.CellClick -> onCellClick(event.index)
            EditPartidaUIEvent.StartGame -> startNewPartida()
            EditPartidaUIEvent.RestartGame -> restartGame()
            EditPartidaUIEvent.SaveGame -> saveGame()
        }
    }

    private fun loadPartida(id: Int?) {
        _state.update { it.copy(partidaId = id) }
        _navigateToEdit.value = id
    }

    private fun selectJugadorX(jugadorId: Int) {
        viewModelScope.launch {
            val jugador = getJugadorUseCase(jugadorId)
            jugador?.let {
                _state.update { state ->
                    state.copy(
                        jugadorXId = jugadorId,
                        jugadores = (state.jugadores + it).distinctBy { j -> j.hashCode() }
                    )
                }
            }
        }
    }

    private fun selectJugadorO(jugadorId: Int) {
        viewModelScope.launch {
            val jugador = getJugadorUseCase(jugadorId)
            jugador?.let {
                _state.update { state ->
                    state.copy(
                        jugadorOId = jugadorId,
                        jugadores = (state.jugadores + it).distinctBy { j -> j.hashCode() }
                    )
                }
            }
        }
    }

    fun startNewPartida() {
        _state.update { it.copy(
            partidaId = null,
            jugadorXId = 0,
            jugadorOId = 0,
            ganadorId = null,
            esFinalizada = false,
            board = List(9) { null },
            currentPlayer = Player.X,
            winner = null,
            isDraw = false,
            gameStarted = true
        ) }
    }


    private fun onCellClick(index: Int) {
        val currentState = _state.value
        if (!currentState.gameStarted || currentState.board[index] != null || currentState.winner != null) return

        val newBoard = currentState.board.toMutableList()
        newBoard[index] = currentState.currentPlayer

        val newWinner = checkWinner(newBoard)
        val isDraw = newBoard.all { it != null } && newWinner == null
        val nextPlayer = if (currentState.currentPlayer == Player.X) Player.O else Player.X

        _state.update {
            it.copy(
                board = newBoard,
                currentPlayer = nextPlayer,
                winner = newWinner,
                isDraw = isDraw
            )
        }

        if (newWinner != null || isDraw) {
            val ganadorId = when (newWinner) {
                Player.X -> currentState.jugadorXId
                Player.O -> currentState.jugadorOId
                null -> null
            }
            _state.update { it.copy(ganadorId = ganadorId, esFinalizada = true) }
        }
    }

    private fun checkWinner(board: List<Player?>): Player? {
        val winningLines = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(0, 4, 8), listOf(2, 4, 6)
        )
        for (line in winningLines) {
            val (a, b, c) = line
            if (board[a] != null && board[a] == board[b] && board[a] == board[c]) return board[a]
        }
        return null
    }

    private fun restartGame() {
        _state.update {
            it.copy(
                board = List(9) { null },
                currentPlayer = Player.X,
                winner = null,
                isDraw = false,
                ganadorId = null,
                esFinalizada = false,
                gameStarted = true
            )
        }
    }

    private fun saveGame() {
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, error = null) }

            try {
                val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val partida = Partida(
                    partidaId = state.value.partidaId ?: 0,
                    fecha = fecha,
                    jugador1Id = state.value.jugadorXId,
                    jugador2Id = state.value.jugadorOId,
                    ganadorId = state.value.ganadorId,
                    esFinalizada = state.value.esFinalizada
                )

                if (state.value.partidaId == null) {
                    insertPartidaUseCase(partida)
                } else {
                    updatePartidaUseCase(partida)
                }

                _state.update { it.copy(saved = true) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }

            _state.update { it.copy(isSaving = false) }
        }
    }

    fun onNavigationComplete() {
        _navigateToEdit.value = null
    }
}
