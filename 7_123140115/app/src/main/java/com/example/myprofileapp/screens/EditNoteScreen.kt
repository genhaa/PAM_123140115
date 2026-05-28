package com.example.myprofileapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myprofileapp.data.NoteDetailUiState
import com.example.myprofileapp.ui.theme.*
import com.example.myprofileapp.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    noteId: Int,
    viewModel: NoteViewModel,
    onSaved: () -> Unit,
    onBackClick: () -> Unit
) {
    LaunchedEffect(noteId) {
        viewModel.getNoteById(noteId)
    }

    val detailState by viewModel.detailState.collectAsStateWithLifecycle()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isDataLoaded by remember { mutableStateOf(false) }

    if (detailState is NoteDetailUiState.Success && !isDataLoaded) {
        val note = (detailState as NoteDetailUiState.Success).note
        title = note.title
        content = note.content
        isDataLoaded = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ubah Catatan", color = MaterialTheme.colorScheme.onSurface) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onSurface)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when (detailState) {
                is NoteDetailUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(androidx.compose.ui.Alignment.Center),
                        color = BoldSage
                    )
                }
                is NoteDetailUiState.Error -> {
                    Text(
                        text = "Gagal memuat catatan untuk diedit.",
                        modifier = Modifier.align(androidx.compose.ui.Alignment.Center),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                is NoteDetailUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Judul") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = BoldSage,
                                unfocusedBorderColor = BoldSage.copy(alpha = 0.5f),
                                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                            )
                        )

                        OutlinedTextField(
                            value = content,
                            onValueChange = { content = it },
                            label = { Text("Isi Catatan") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = BoldSage,
                                unfocusedBorderColor = BoldSage.copy(alpha = 0.5f),
                                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            OutlinedButton(
                                onClick = onBackClick,
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = BoldSage)
                            ) {
                                Text("Batal")
                            }
                            Button(
                                onClick = {
                                    if (title.isNotBlank() && content.isNotBlank()) {
                                        viewModel.updateNote(noteId, title, content)
                                        onSaved()
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = BoldSage)
                            ) {
                                Text("Simpan", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}