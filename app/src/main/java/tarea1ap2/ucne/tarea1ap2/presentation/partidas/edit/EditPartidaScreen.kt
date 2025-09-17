package tarea1ap2.ucne.tarea1ap2.presentation.partidas.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPartidaScreen(
    partidaId: Int?,
    onBack: () -> Unit,
    viewModel: EditPartidaViewModel = hiltViewModel()
) {
    val state = viewModel.state

    LaunchedEffect(partidaId) {
        viewModel.onEvent(EditPartidaUIEvent.Load(partidaId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Partida") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            if (!state.collectAsState().value.gameStarted) {
                PlayerSelectionSection(state.collectAsState().value, viewModel::onEvent)
            } else {
                GameBoardSection(state.collectAsState().value, viewModel::onEvent)
            }

            state.collectAsState().value.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }

    if (state.collectAsState().value.saved) {
        LaunchedEffect(Unit) {
            onBack()
        }
    }
}

@Composable
private fun PlayerSelectionSection(
    state: EditPartidaUiState,
    onEvent: (EditPartidaUIEvent) -> Unit
) {
    Text("Seleccionar Jugadores", fontSize = 20.sp, fontWeight = FontWeight.Bold)

    Spacer(modifier = Modifier.height(16.dp))

    Text("Jugador X:", fontWeight = FontWeight.Bold)
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(state.jugadores) { jugador ->
            FilterChip(
                selected = state.jugadorXId == jugador.jugadorId,
                onClick = { onEvent(EditPartidaUIEvent.JugadorXSelected(jugador.jugadorId)) },
                label = { Text(jugador.nombres) },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text("Jugador O:", fontWeight = FontWeight.Bold)
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(state.jugadores) { jugador ->
            FilterChip(
                selected = state.jugadorOId == jugador.jugadorId,
                onClick = { onEvent(EditPartidaUIEvent.JugadorOSelected(jugador.jugadorId)) },
                label = { Text(jugador.nombres) },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Button(
        onClick = { onEvent(EditPartidaUIEvent.StartGame) },
        enabled = state.jugadorXId != 0 && state.jugadorOId != 0 && state.jugadorXId != state.jugadorOId,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Iniciar Partida")
    }
}

@Composable
private fun GameBoardSection(
    state: EditPartidaUiState,
    onEvent: (EditPartidaUIEvent) -> Unit
) {
    val gameStatus = when {
        state.winner != null -> "¡Ganador: ${state.winner.symbol}!"
        state.isDraw -> "¡Es un empate!"
        else -> "Turno de: ${state.currentPlayer.symbol}"
    }

    Text(text = gameStatus, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))

    GameBoard(board = state.board, onCellClick = { onEvent(EditPartidaUIEvent.CellClick(it)) })
    Spacer(modifier = Modifier.height(16.dp))

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { onEvent(EditPartidaUIEvent.RestartGame) },
            modifier = Modifier.weight(1f)
        ) {
            Text("Reiniciar")
        }

        Button(
            onClick = { onEvent(EditPartidaUIEvent.SaveGame) },
            enabled = !state.isSaving && state.esFinalizada,
            modifier = Modifier.weight(1f)
        ) {
            if (state.isSaving) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text("Guardar")
            }
        }
    }
}

@Composable
private fun GameBoard(
    board: List<Player?>,
    onCellClick: (Int) -> Unit
) {
    Column {
        (0..2).forEach { row ->
            Row {
                (0..2).forEach { col ->
                    val index = row * 3 + col
                    BoardCell(player = board[index], onClick = { onCellClick(index) })
                }
            }
        }
    }
}

@Composable
private fun BoardCell(
    player: Player?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(4.dp)
            .background(Color.LightGray)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = player?.symbol ?: "",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = if (player == Player.X) Color.Blue else Color.Red
        )
    }
}

@Preview
@Composable
private fun EditPartidaScreenPreview() {
    MaterialTheme {
        EditPartidaScreen(partidaId = null, onBack = {})
    }
}