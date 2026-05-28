package com.example.myprofileapp.data

import com.example.myprofileapp.database.NoteEntity

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false
)

fun NoteEntity.toDomain() = Note(
    id = id.toInt(),
    title = title,
    content = content,
    isFavorite = isFavorite == 1L
)

sealed interface NoteListUiState {
    object Loading : NoteListUiState
    data class Empty(val message: String) : NoteListUiState
    data class Success(val notes: List<Note>) : NoteListUiState
}

sealed interface NoteDetailUiState {
    object Loading : NoteDetailUiState
    object Error : NoteDetailUiState
    data class Success(val note: Note) : NoteDetailUiState
}