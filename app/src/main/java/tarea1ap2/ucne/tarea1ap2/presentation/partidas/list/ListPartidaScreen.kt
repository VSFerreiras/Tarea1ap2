package tarea1ap2.ucne.tarea1ap2.presentation.partidas.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import tarea1ap2.ucne.tarea1ap2.domain.model.Partida

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPartidaScreen(
    onAddPartida: () -> Unit,
    onEditPartida: (Int) -> Unit,
    viewModel: ListPartidaViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Historial de Partidas") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddPartida) {
                Icon(Icons.Default.Add, contentDescription = "Nueva Partida")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.partidas) { partida: Partida ->
                        PartidaListItem(
                            partida = partida,
                            onEdit = { onEditPartida(partida.partidaId) },
                            onDelete = { viewModel.onEvent(ListPartidaUIEvent.Delete(partida)) }
                        )
                        Divider()
                    }
                }
            }

            state.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun PartidaListItem(
    partida: Partida,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        onClick = onEdit,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Partida #${partida.partidaId} - ${partida.fecha}",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Jugador X vs Jugador O")
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (partida.ganadorId != null)
                    "Ganador: Jugador ${if (partida.ganadorId == partida.jugador1Id) "X" else "O"}"
                else "Empate",
                fontWeight = FontWeight.Bold,
                color = if (partida.ganadorId != null) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (partida.esFinalizada) "Finalizada" else "En progreso",
                color = if (partida.esFinalizada) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.error
            )
        }
    }
}
