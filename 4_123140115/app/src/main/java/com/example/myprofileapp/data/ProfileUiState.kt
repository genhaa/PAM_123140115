package com.example.myprofileapp.data

data class ProfileUiState(
    val name: String = "Grace Exauditha",
    val role: String = "Mahasiswa Teknik Informatika @ ITERA",
    val bio: String = "Mahasiswa Teknik Informatika Itera 2023. Minat di bidang jaringan dan keamanan komputer. Suka eksplorasi hal baru!",
    val email: String = "grace.123140115@student.itera.ac.id",
    val phone: String = "+62 812-7956-0512",
    val location: String = "Bandar Lampung, Indonesia",
    val isDarkMode: Boolean = false,
    val isEditing: Boolean = false
)