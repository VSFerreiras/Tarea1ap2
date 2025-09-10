package edu.ucne.tarea1ap2.presentation.jugadores.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import tarea1ap2.ucne.tarea1ap2.presentation.jugadores.edit.JugadorEditUiEvent
import tarea1ap2.ucne.tarea1ap2.presentation.jugadores.edit.JugadorEditUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JugadorEditScreen(
    state: JugadorEditUiState,
    onEvent: (JugadorEditUiEvent) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isNew) "Nuevo Jugador" else "Editar Jugador") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = padding)
                .padding(all = 16.dp)
        ) {
            // Campo de nombre
            OutlinedTextField(
                value = state.nombres,
                onValueChange = { onEvent(JugadorEditUiEvent.NombresChanged(value = it)) },
                label = { Text("Nombre completo") },
                isError = state.nombresError != null,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (state.nombresError != null) {
                Text(
                    text = state.nombresError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de partidas (AHORA EDITABLE)
            OutlinedTextField(
                value = state.partidas.toString(),
                onValueChange = { onEvent(JugadorEditUiEvent.PartidasChanged(value = it)) },
                label = { Text("Partidas jugadas") },
                isError = state.partidasError != null,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = { Text("Máximo 100,000") }
            )

            if (state.partidasError != null) {
                Text(
                    text = state.partidasError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            } else {
                Text(
                    text = "Máximo permitido: 100,000 partidas",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onEvent(JugadorEditUiEvent.Save) },
                enabled = !state.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp))
                } else {
                    Text("Guardar Jugador")
                }
            }

            if (!state.isNew) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onEvent(JugadorEditUiEvent.Delete) },
                    enabled = !state.isDeleting,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    if (state.isDeleting) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    } else {
                        Text("Eliminar Jugador")
                    }
                }
            }
        }
    }
}
