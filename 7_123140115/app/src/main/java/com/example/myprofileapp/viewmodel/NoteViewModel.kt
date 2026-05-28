package com.example.myprofileapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprofileapp.data.Note
import com.example.myprofileapp.data.NoteListUiState
import com.example.myprofileapp.data.NoteDetailUiState
import com.example.myprofileapp.data.NoteRepository
import com.example.myprofileapp.data.SettingsRepository
import com.example.myprofileapp.data.SettingsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository = NoteRepository(application.applicationContext)
    private val settingsRepository = SettingsRepository(application.applicationContext)

    val settingsState: StateFlow<SettingsUiState> = settingsRepository.settingsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsUiState(isDarkMode = false, sortOrder = "LATEST")
        )

    private val _allNotes = MutableStateFlow<List<Note>>(emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val notesListState: StateFlow<NoteListUiState> = combine(
        _allNotes,
        _searchQuery,
        settingsState
    ) { notes, query, settings ->
        if (notes.isEmpty() && query.isEmpty()) {
            NoteListUiState.Success(emptyList())
        } else {
            var filteredNotes = if (query.isBlank()) {
                notes
            } else {
                notes.filter {
                    it.title.contains(query, ignoreCase = true) ||
                            it.content.contains(query, ignoreCase = true)
                }
            }

            filteredNotes = when (settings.sortOrder) {
                "LATEST" -> filteredNotes.sortedByDescending { it.id }
                "OLDEST" -> filteredNotes.sortedBy { it.id }
                "ALPHABETICAL" -> filteredNotes.sortedBy { it.title.lowercase() }
                else -> filteredNotes
            }

            NoteListUiState.Success(filteredNotes)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NoteListUiState.Loading
    )

    private val _detailState = MutableStateFlow<NoteDetailUiState>(NoteDetailUiState.Loading)
    val detailState: StateFlow<NoteDetailUiState> = _detailState.asStateFlow()

    init {
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            try {
                val notes = noteRepository.getAllNotes()
                _allNotes.value = notes
            } catch (e: Exception) {
                _allNotes.value = emptyList()
            }
        }
    }

    fun getNoteById(id: Int) {
        viewModelScope.launch {
            _detailState.value = NoteDetailUiState.Loading
            try {
                val note = noteRepository.getNoteById(id)
                if (note != null) {
                    _detailState.value = NoteDetailUiState.Success(note)
                } else {
                    _detailState.value = NoteDetailUiState.Error
                }
            } catch (e: Exception) {
                _detailState.value = NoteDetailUiState.Error
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun setSortOrder(order: String) {
        viewModelScope.launch {
            settingsRepository.changeSortOrder(order)
            loadNotes()
        }
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            noteRepository.insertNote(title, content, isFavorite = false)
            loadNotes()
        }
    }

    fun updateNote(id: Int, title: String, content: String) {
        viewModelScope.launch {
            val existingNote = noteRepository.getNoteById(id)
            val currentFav = existingNote?.isFavorite ?: false
            noteRepository.updateNote(id, title, content, isFavorite = currentFav)
            loadNotes()
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            noteRepository.deleteNote(id)
            loadNotes()
        }
    }

    fun toggleTheme(isDark: Boolean) {
        viewModelScope.launch {
            settingsRepository.toggleTheme(isDark)
        }
    }

    fun toggleFavorite(noteId: Int) {
        viewModelScope.launch {
            val existingNote = noteRepository.getNoteById(noteId)
            if (existingNote != null) {
                noteRepository.updateNote(
                    id = existingNote.id,
                    title = existingNote.title,
                    content = existingNote.content,
                    isFavorite = !existingNote.isFavorite
                )
                loadNotes()
            }
        }
    }
}