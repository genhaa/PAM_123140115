package com.example.myprofileapp.data

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.myprofileapp.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepository(context: Context) {
    private val driver = AndroidSqliteDriver(
        schema = AppDatabase.Schema,
        context = context.applicationContext,
        name = "notes.db"
    )
    private val database = AppDatabase(driver)
    private val queries = database.noteEntityQueries

    suspend fun getAllNotes(): List<Note> = withContext(Dispatchers.IO) {
        queries.getAllNotes().executeAsList().map { it.toDomain() }
    }

    suspend fun getNoteById(id: Int): Note? = withContext(Dispatchers.IO) {
        queries.getNoteById(id.toLong()).executeAsOneOrNull()?.toDomain()
    }

    suspend fun insertNote(title: String, content: String, isFavorite: Boolean): Long = withContext(Dispatchers.IO) {
        queries.insertNote(title, content, if (isFavorite) 1L else 0L)
        queries.getLastInsertedId().executeAsOne()
    }

    suspend fun updateNote(id: Int, title: String, content: String, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        queries.updateNote(title, content, if (isFavorite) 1L else 0L, id.toLong())
    }

    suspend fun deleteNote(id: Int) = withContext(Dispatchers.IO) {
        queries.deleteNote(id.toLong())
    }
}