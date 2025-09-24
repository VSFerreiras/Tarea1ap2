package tarea1ap2.ucne.tarea1ap2.presentation.logros

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import tarea1ap2.ucne.tarea1ap2.domain.model.Logro
import tarea1ap2.ucne.tarea1ap2.domain.repository.LogroRepository
import javax.inject.Inject

@HiltViewModel
class LogroViewModel @Inject constructor(
    private val repository: LogroRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LogroUiState())
    val state: StateFlow<LogroUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeLogros().collect { logros ->
                _state.update { it.copy(logros = logros) }
            }
        }
    }

    fun onEvent(event: LogroEvent) {
        when (event) {
            is LogroEvent.Load -> {
                if (event.id != null) {
                    viewModelScope.launch {
                        repository.getLogro(event.id)?.let { logro ->
                            _state.update {
                                it.copy(
                                    editingId = logro.logroId,
                                    titulo = logro.titulo,
                                    descripcion = logro.descripcion,
                                    isCreating = false
                                )
                            }
                        }
                    }
                }
            }
            is LogroEvent.Save -> {
                viewModelScope.launch {
                    repository.upsert(
                        Logro(
                            logroId = _state.value.editingId,
                            titulo = event.titulo,
                            descripcion = event.descripcion
                        )
                    )
                    _state.update {
                        it.copy(
                            saved = true,
                            editingId = null,
                            isCreating = false
                        )
                    }
                }
            }
            is LogroEvent.Delete -> {
                viewModelScope.launch {
                    repository.delete(event.id)
                    _state.update {
                        it.copy(
                            deleted = true,
                            editingId = null,
                            isCreating = false
                        )
                    }
                }
            }
            is LogroEvent.New -> {
                _state.update {
                    it.copy(
                        editingId = null,
                        titulo = "",
                        descripcion = "",
                        isCreating = true,
                        saved = false,
                        deleted = false
                    )
                }
            }
        }
    }

    fun onNavigationComplete() {
        _state.update {
            it.copy(
                saved = false,
                deleted = false,
                editingId = null,
                isCreating = false
            )
        }
    }
}