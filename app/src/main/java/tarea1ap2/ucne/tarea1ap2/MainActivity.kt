package edu.ucne.tarea1ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import tarea1ap2.ucne.tarea1ap2.presentation.logros.LogroScreen
import tarea1ap2.ucne.tarea1ap2.presentation.logros.LogroViewModel
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
                    val viewModel: LogroViewModel = hiltViewModel()
                    val state by viewModel.state.collectAsState()

                    LogroScreen(
                        state = state,
                        onEvent = viewModel::onEvent,
                        onBack = { viewModel.onNavigationComplete() }
                    )
                }
            }
        }
    }
}