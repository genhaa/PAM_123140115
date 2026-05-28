package com.example.myprofileapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myprofileapp.data.NoteDetailUiState
import com.example.myprofileapp.ui.theme.*
import androidx.compose.ui.graphics.Color
import com.example.myprofileapp.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: Int,
    viewModel: NoteViewModel,
    onEditClick: () -> Unit,
    onBackClick: () -> Unit
) {
    LaunchedEffect(noteId) {
        viewModel.getNoteById(noteId)
    }

    val detailState by viewModel.detailState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Catatan", color = MaterialTheme.colorScheme.onSurface) },
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
            when (val state = detailState) {
                is NoteDetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = BoldSage)
                }
                is NoteDetailUiState.Error -> {
                    Text("Gagal memuat catatan.", modifier = Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.onBackground)
                }
                is NoteDetailUiState.Success -> {
                    val note = state.note
                    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
                        Text(note.title, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(note.content, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground, lineHeight = 24.sp)

                        Spacer(modifier = Modifier.weight(1f))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(
                                onClick = onEditClick,
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = BoldSage)
                            ) {
                                Text("Edit Catatan", color = Color.White)
                            }
                            Button(
                                onClick = {
                                    viewModel.deleteNote(note.id)
                                    onBackClick()
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = HotPink)
                            ) {
                                Text("Hapus Log", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}