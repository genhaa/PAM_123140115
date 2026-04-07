package com.example.myprofileapp.ui.profilescreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myprofileapp.ui.theme.*
import com.example.myprofileapp.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var tempName by remember { mutableStateOf(uiState.name) }
    var tempBio  by remember { mutableStateOf(uiState.bio)  }

    LaunchedEffect(uiState.name, uiState.bio) {
        tempName = uiState.name
        tempBio  = uiState.bio
    }

    val bgColor = if (uiState.isDarkMode) Color(0xFF1A1A1A) else SoftSage

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .verticalScroll(rememberScrollState())
    ) {
        // Dark Mode Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .statusBarsPadding(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DayNightToggle(
                isDarkMode = uiState.isDarkMode,
                onToggle = { viewModel.toggleDarkMode() }
            )
        }

        // Header
        ProfileHeader(
            name = uiState.name,
            role = uiState.role,
            isDarkMode = uiState.isDarkMode
        )

        // About Me / Edit Form
        if (uiState.isEditing) {
            EditProfileCard(
                name = tempName,
                bio = tempBio,
                isDarkMode = uiState.isDarkMode,
                onNameChange = { tempName = it },
                onBioChange  = { tempBio  = it },
                onSave   = { viewModel.updateProfile(tempName, tempBio) },
                onCancel = { viewModel.toggleEditMode() }
            )
        } else {
            ProfileCard(title = "About Me", isDarkMode = uiState.isDarkMode) {
                Text(
                    text = uiState.bio,
                    fontSize = 14.sp,
                    color = if (uiState.isDarkMode) Color.LightGray else Color.Gray,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { viewModel.toggleEditMode() },
                    colors = ButtonDefaults.buttonColors(containerColor = BoldSage)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Edit Profile")
                }
            }
        }

        // Contact Info
        ProfileCard(title = "Contact Info", isDarkMode = uiState.isDarkMode) {
            InfoItem(Icons.Default.Email, "EMAIL", uiState.email, uiState.isDarkMode)
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = if (uiState.isDarkMode) Color(0xFF333333) else SoftSage
            )
            InfoItem(Icons.Default.Phone, "PHONE", uiState.phone, uiState.isDarkMode)
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = if (uiState.isDarkMode) Color(0xFF333333) else SoftSage
            )
            InfoItem(Icons.Default.LocationOn, "LOCATION", uiState.location, uiState.isDarkMode)
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun DayNightToggle(
    isDarkMode: Boolean,
    onToggle: () -> Unit
) {
    val toggleColor by animateColorAsState(
        targetValue = if (isDarkMode) Color(0xFF3D5A99) else Color(0xFFFF9800),
        animationSpec = tween(durationMillis = 300),
        label = "toggleColor"
    )

    val thumbOffset by animateDpAsState(
        targetValue = if (isDarkMode) 30.dp else 2.dp,
        animationSpec = tween(durationMillis = 300),
        label = "thumbOffset"
    )

    Box(
        modifier = Modifier
            .width(72.dp)
            .height(40.dp)
            .clip(CircleShape)
            .background(if (isDarkMode) Color(0xFF1E2A3A) else Color(0xFFF0F0F0))
            .clickable { onToggle() },
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .padding(start = thumbOffset)
                .size(36.dp)
                .clip(CircleShape)
                .background(toggleColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isDarkMode) Icons.Default.DarkMode
                else Icons.Default.LightMode,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun EditProfileCard(
    name: String,
    bio: String,
    isDarkMode: Boolean,
    onNameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    val cardColor = if (isDarkMode) Color(0xFF2A2A2A) else WhiteSage

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "Edit Profile",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDarkMode) Color.White else TextDark
            )
            Spacer(modifier = Modifier.height(16.dp))

            // TextField Name — Stateless (State Hoisting)
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = if (isDarkMode) Color.White else TextDark,
                    unfocusedTextColor = if (isDarkMode) Color.White else TextDark,
                    focusedLabelColor = if (isDarkMode) Color.LightGray else BoldSage,
                    unfocusedLabelColor = if (isDarkMode) Color.LightGray else BoldSage,
                    focusedBorderColor = if (isDarkMode) Color.LightGray else BoldSage,
                    unfocusedBorderColor = if (isDarkMode) Color(0xFF555555) else BoldSage,
                    cursorColor = if (isDarkMode) Color.White else BoldSage
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            // TextField Bio — Stateless (State Hoisting)
            OutlinedTextField(
                value = bio,
                onValueChange = onBioChange,
                label = { Text("Bio") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = if (isDarkMode) Color.White else TextDark,
                    unfocusedTextColor = if (isDarkMode) Color.White else TextDark,
                    focusedLabelColor = if (isDarkMode) Color.LightGray else BoldSage,
                    unfocusedLabelColor = if (isDarkMode) Color.LightGray else BoldSage,
                    focusedBorderColor = if (isDarkMode) Color.LightGray else BoldSage,
                    unfocusedBorderColor = if (isDarkMode) Color(0xFF555555) else BoldSage,
                    cursorColor = if (isDarkMode) Color.White else BoldSage
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(onClick = onCancel) { Text("Cancel") }
                Button(
                    onClick = onSave,
                    colors = ButtonDefaults.buttonColors(containerColor = BoldSage)
                ) { Text("Save") }
            }
        }
    }
}

@Composable
fun ProfileHeader(name: String, role: String, isDarkMode: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = if (isDarkMode)
                        listOf(Color(0xFF2A2A2A), Color(0xFF1A1A1A))
                    else
                        listOf(Color(0xFFC1DBC1), SoftSage)
                )
            )
            .padding(top = 16.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(110.dp)
                .border(
                    width = 4.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(HotPink, SunnyYellow)
                    ),
                    shape = CircleShape
                )
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(if (isDarkMode) Color(0xFF333333) else Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = BoldSage,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            name,
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = if (isDarkMode) Color.White else TextDark
        )
        Text(
            role,
            fontSize = 14.sp,
            color = HotPink,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )
    }
}

@Composable
fun ProfileCard(
    title: String,
    isDarkMode: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    val cardColor = if (isDarkMode) Color(0xFF2A2A2A) else WhiteSage
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(width = 4.dp, height = 18.dp)
                        .clip(CircleShape)
                        .background(SunnyYellow)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isDarkMode) Color.White else TextDark
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
fun InfoItem(
    icon: ImageVector,
    label: String,
    value: String,
    isDarkMode: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(if (isDarkMode) Color(0xFF333333) else SoftSage),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = BoldSage,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                label,
                fontSize = 10.sp,
                color = BoldSage,
                fontWeight = FontWeight.Bold
            )
            Text(
                value,
                fontSize = 15.sp,
                color = if (isDarkMode) Color.White else TextDark,
                fontWeight = FontWeight.Medium
            )
        }
    }
}