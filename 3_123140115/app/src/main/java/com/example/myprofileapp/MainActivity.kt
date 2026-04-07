package com.example.myprofileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofileapp.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyProfileAppTheme {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun ProfileHeader(
    name: String,
    role: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFC1DBC1), SoftSage)
                )
            )
            .padding(top = 60.dp, bottom = 32.dp),
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
                    .background(Color.White),
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
            text = name,
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextDark
        )

        Text(
            text = role,
            fontSize = 14.sp,
            color = HotPink,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )
    }
}

@Composable
fun InfoItem(
    icon: ImageVector,
    label: String,
    value: String
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
                .background(SoftSage),
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
                text = label,
                fontSize = 10.sp,
                color = BoldSage,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = value,
                fontSize = 15.sp,
                color = TextDark,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ProfileCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = WhiteSage),
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
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftSage)
            .verticalScroll(rememberScrollState())
    ) {
        ProfileHeader(
            name = "Grace Exauditha",
            role = "Mahasiswa Teknik Informatika @ ITERA"
        )

        ProfileCard(title = "About Me") {
            Text(
                text = "Mahasiswa Teknik Informatika Itera 2023. Minat di bidang jaringan dan keamanan komputer. Suka eksplorasi hal baru!",
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 20.sp
            )
        }

        ProfileCard(title = "Contact Info") {
            InfoItem(Icons.Default.Email, "EMAIL", "grace.123140115@student.itera.ac.id")
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = SoftSage)
            InfoItem(Icons.Default.Phone, "PHONE", "+62 812-7956-0512")
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = SoftSage)
            InfoItem(Icons.Default.LocationOn, "LOCATION", "Bandar Lampung, Indonesia")
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
fun LightPreview() {
    MyProfileAppTheme {
        ProfileScreen()
    }
}