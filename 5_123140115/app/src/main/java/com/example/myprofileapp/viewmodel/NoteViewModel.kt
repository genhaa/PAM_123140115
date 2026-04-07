package com.example.myprofileapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myprofileapp.data.Note
import com.example.myprofileapp.data.NoteUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NoteViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    private var nextId = 6

    fun getNoteById(id: Int): Note? {
        return _uiState.value.notes.find { it.id == id }
    }

    fun addNote(title: String, content: String) {
        val newNote = Note(id = nextId++, title = title, content = content)
        _uiState.update { it.copy(notes = it.notes + newNote) }
    }

    fun updateNote(id: Int, title: String, content: String) {
        _uiState.update { state ->
            state.copy(
                notes = state.notes.map { note ->
                    if (note.id == id) note.copy(title = title, content = content) else note
                }
            )
        }
    }

    fun deleteNote(id: Int) {
        _uiState.update { state ->
            state.copy(notes = state.notes.filter { it.id != id })
        }
    }

    fun toggleFavorite(id: Int) {
        _uiState.update { state ->
            state.copy(
                notes = state.notes.map { note ->
                    if (note.id == id) note.copy(isFavorite = !note.isFavorite) else note
                }
            )
        }
    }
}