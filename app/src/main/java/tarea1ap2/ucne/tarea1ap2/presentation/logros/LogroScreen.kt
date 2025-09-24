package tarea1ap2.ucne.tarea1ap2.presentation.logros

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tarea1ap2.ucne.tarea1ap2.domain.model.Logro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogroScreen(
    state: LogroUiState,
    onEvent: (LogroEvent) -> Unit,
    onBack: () -> Unit
) {
    val isEditing = state.editingId != null
    val isCreating = state.isCreating

    if (!isEditing && !isCreating) {
        LogroListScreen(state, onEvent)
    } else {
        LogroEditScreen(state, onEvent, onBack)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LogroListScreen(
    state: LogroUiState,
    onEvent: (LogroEvent) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Lista de Logros") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(LogroEvent.New) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar logro"
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(state.logros) { logro ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    onClick = { onEvent(LogroEvent.Load(logro.logroId)) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(logro.titulo, style = MaterialTheme.typography.titleLarge)
                        Text(logro.descripcion, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LogroEditScreen(
    state: LogroUiState,
    onEvent: (LogroEvent) -> Unit,
    onBack: () -> Unit
) {
    var titulo by remember { mutableStateOf(state.titulo) }
    var descripcion by remember { mutableStateOf(state.descripcion) }

    LaunchedEffect(state.editingId, state.titulo, state.descripcion) {
        titulo = state.titulo
        descripcion = state.descripcion
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(if (state.editingId != null) "Editar Logro" else "Nuevo Logro")
            })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    onEvent(LogroEvent.Save(titulo, descripcion))
                }) {
                    Text("Guardar")
                }
                if (state.editingId != null) {
                    Button(onClick = {
                        onEvent(LogroEvent.Delete(state.editingId))
                    }) {
                        Text("Eliminar")
                    }
                }
                OutlinedButton(onClick = { onBack() }) {
                    Text("Cancelar")
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LogroListPreview() {
    LogroListScreen(
        state = LogroUiState(
            logros = listOf(
                Logro(1, "Campeón", "Ganó torneo"),
                Logro(2, "Goleador", "Anotó 20 goles")
            )
        ),
        onEvent = {}
    )
}

@Preview(showSystemUi = true)
@Composable
fun LogroEditPreview() {
    LogroEditScreen(
        state = LogroUiState(
            editingId = 1,
            titulo = "Campeón",
            descripcion = "Ganó torneo"
        ),
        onEvent = {},
        onBack = {}
    )
}

@Preview(showSystemUi = true)
@Composable
fun LogroCreatePreview() {
    LogroEditScreen(
        state = LogroUiState(
            isCreating = true,
            titulo = "",
            descripcion = ""
        ),
        onEvent = {},
        onBack = {}
    )
}