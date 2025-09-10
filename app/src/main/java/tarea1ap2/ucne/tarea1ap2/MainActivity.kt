package edu.ucne.tarea1ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.tarea1ap2.presentation.jugadores.edit.JugadorEditScreen
import edu.ucne.tarea1ap2.presentation.jugadores.list.JugadorListViewModel
import tarea1ap2.ucne.tarea1ap2.presentation.jugadores.edit.JugadorEditUiEvent
import tarea1ap2.ucne.tarea1ap2.presentation.jugadores.edit.JugadorEditViewModel
import tarea1ap2.ucne.tarea1ap2.presentation.jugadores.list.JugadorListScreen
import tarea1ap2.ucne.tarea1ap2.presentation.jugadores.list.JugadorListUiEvent
import tarea1ap2.ucne.tarea1ap2.ui.theme.Tarea1ap2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tarea1ap2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val listViewModel: JugadorListViewModel = hiltViewModel()
                    val editViewModel: JugadorEditViewModel = hiltViewModel()

                    val navigateToEdit by listViewModel.navigateToEdit.collectAsState()
                    val listState by listViewModel.state.collectAsState()
                    val editState by editViewModel.state.collectAsState()

                    if (navigateToEdit == null) {
                        JugadorListScreen(
                            state = listState,
                            onEvent = listViewModel::onEvent,
                            onEdit = { id -> listViewModel.onEvent(JugadorListUiEvent.Edit(id)) },
                            onBack = { finish() } // ← Cerrar app al retroceder
                        )
                    } else {
                        LaunchedEffect(navigateToEdit) {
                            if (navigateToEdit != null && navigateToEdit != -1) {
                                editViewModel.onEvent(JugadorEditUiEvent.Load(navigateToEdit))
                            } else {
                                editViewModel.onEvent(JugadorEditUiEvent.Load(null))
                            }
                        }

                        JugadorEditScreen(
                            state = editState,
                            onEvent = { event ->
                                when (event) {
                                    is JugadorEditUiEvent.Save -> {
                                        editViewModel.onEvent(event)
                                        if (editState.saved) {
                                            listViewModel.onNavigationComplete()
                                        }
                                    }
                                    is JugadorEditUiEvent.Delete -> {
                                        editViewModel.onEvent(event)
                                        if (editState.deleted) {
                                            listViewModel.onNavigationComplete()
                                        }
                                    }
                                    else -> editViewModel.onEvent(event)
                                }
                            },
                            onBack = { listViewModel.onNavigationComplete() } // ← Volver a lista
                        )
                    }
                }
            }
        }
    }
}