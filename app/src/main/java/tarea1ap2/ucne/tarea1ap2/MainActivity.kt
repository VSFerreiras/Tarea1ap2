package edu.ucne.tarea1ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.tarea1ap2.presentation.jugadores.edit.JugadorEditScreen
import edu.ucne.tarea1ap2.presentation.jugadores.list.JugadorListViewModel
import tarea1ap2.ucne.tarea1ap2.presentation.partidas.edit.EditPartidaScreen
import tarea1ap2.ucne.tarea1ap2.presentation.partidas.edit.EditPartidaUIEvent
import tarea1ap2.ucne.tarea1ap2.presentation.partidas.edit.EditPartidaViewModel
import tarea1ap2.ucne.tarea1ap2.presentation.partidas.list.ListPartidaScreen
import tarea1ap2.ucne.tarea1ap2.presentation.partidas.list.ListPartidaViewModel
import tarea1ap2.ucne.tarea1ap2.ui.theme.Tarea1ap2Theme
import kotlinx.coroutines.launch
import tarea1ap2.ucne.tarea1ap2.presentation.jugadores.edit.JugadorEditUiEvent
import tarea1ap2.ucne.tarea1ap2.presentation.jugadores.edit.JugadorEditViewModel
import tarea1ap2.ucne.tarea1ap2.presentation.jugadores.list.JugadorListScreen
import tarea1ap2.ucne.tarea1ap2.presentation.jugadores.list.JugadorListUiEvent
import tarea1ap2.ucne.tarea1ap2.presentation.partidas.list.ListPartidaUIEvent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tarea1ap2Theme {
                val listViewModel: JugadorListViewModel = hiltViewModel()
                val editViewModel: JugadorEditViewModel = hiltViewModel()
                val partidaListViewModel: ListPartidaViewModel = hiltViewModel()
                val partidaEditViewModel: EditPartidaViewModel = hiltViewModel()

                val navigateToEdit by listViewModel.navigateToEdit.collectAsState()
                val navigateToPartidas by listViewModel.navigateToPartidas.collectAsState()
                val navigateToEditPartida by partidaListViewModel.navigateToEdit.collectAsState()

                val listState by listViewModel.state.collectAsState()
                val editState by editViewModel.state.collectAsState()
                val partidaListState by partidaListViewModel.state.collectAsState()
                val partidaEditState by partidaEditViewModel.state.collectAsState()

                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                var currentScreen by remember { mutableStateOf("jugadores") }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            TextButton(onClick = {
                                scope.launch { drawerState.close() }
                                currentScreen = "jugadores"
                            }) {
                                Text("Jugadores")
                            }
                            TextButton(onClick = {
                                scope.launch { drawerState.close() }
                                currentScreen = "partidas"
                                listViewModel.onEvent(JugadorListUiEvent.ViewPartidas)
                            }) {
                                Text("Partidas")
                            }
                        }
                    }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(when(currentScreen) {
                                        "jugadores" -> "Gestión de Jugadores"
                                        "partidas" -> "Gestión de Partidas"
                                        else -> "Gestión"
                                    })
                                },
                                navigationIcon = {
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                                    }
                                }
                            )
                        }
                    ) { padding ->
                        Box(modifier = Modifier.padding(padding)) {
                            when {
                                navigateToEditPartida != null -> {
                                    LaunchedEffect(navigateToEditPartida) {
                                        if (navigateToEditPartida != null) {
                                            partidaEditViewModel.onEvent(
                                                EditPartidaUIEvent.Load(navigateToEditPartida)
                                            )
                                        }
                                    }
                                    EditPartidaScreen(
                                        partidaId = navigateToEditPartida,
                                        viewModel = partidaEditViewModel,
                                        onBack = {
                                            partidaListViewModel.onNavigationComplete()
                                            currentScreen = "partidas"
                                        }
                                    )
                                }

                                currentScreen == "partidas" -> {
                                    ListPartidaScreen(
                                        onAddPartida = {
                                            partidaEditViewModel.startNewPartida()
                                        },
                                        onEditPartida = { id ->
                                            partidaEditViewModel.onEvent(EditPartidaUIEvent.Load(id))
                                        },
                                        viewModel = partidaListViewModel
                                    )

                                }

                                navigateToEdit != null -> {
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
                                                        currentScreen = "jugadores"
                                                    }
                                                }
                                                is JugadorEditUiEvent.Delete -> {
                                                    editViewModel.onEvent(event)
                                                    if (editState.deleted) {
                                                        listViewModel.onNavigationComplete()
                                                        currentScreen = "jugadores"
                                                    }
                                                }
                                                else -> editViewModel.onEvent(event)
                                            }
                                        },
                                        onBack = {
                                            listViewModel.onNavigationComplete()
                                            currentScreen = "jugadores"
                                        }
                                    )
                                }

                                else -> {
                                    JugadorListScreen(
                                        state = listState,
                                        onEvent = listViewModel::onEvent,
                                        onEdit = { id -> listViewModel.onEvent(JugadorListUiEvent.Edit(id)) },
                                        onBack = { finish() },
                                        onViewPartidas = {
                                            currentScreen = "partidas"
                                            listViewModel.onEvent(JugadorListUiEvent.ViewPartidas)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}