package com.example.myprofileapp.data

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false
)

data class NoteUiState(
    val notes: List<Note> = listOf(
        Note(1, "Belajar Jetpack Compose", "Compose adalah toolkit UI modern untuk Android. Kita bisa bikin UI dengan cara deklaratif menggunakan Kotlin.", isFavorite = true),
        Note(2, "Tips Jaringan Komputer", "OSI Model terdiri dari 7 layer: Physical, Data Link, Network, Transport, Session, Presentation, Application.", isFavorite = false),
        Note(3, "Catatan Kriptografi", "Enkripsi simetris menggunakan kunci yang sama untuk enkripsi dan dekripsi. Contoh: AES, DES.", isFavorite = true),
        Note(4, "Reminder Tugas", "- Kumpulkan tugas Pemrograman Mobile\n- Review materi Keamanan Jaringan\n- Baca paper tentang Zero Trust Architecture", isFavorite = false),
        Note(5, "Quote Favorit", "\"The only way to do great work is to love what you do.\" - Steve Jobs", isFavorite = true)
    )
)