# 📓 MyNotes App — Week 5

Pengembangan Notes App dengan fitur navigasi menggunakan Jetpack Compose Navigation.

---

## 👤 Identitas

| | |
|---|---|
| **Nama** | Grace Exauditha |
| **NIM** | 123140115 |
| **Mata Kuliah** | Pemrograman Mobile |
| **Branch** | `week-5` |

---

## 📋 Deskripsi Tugas

Mengembangkan Notes App dari minggu lalu dengan menambahkan sistem navigasi lengkap:

1. **Bottom Navigation** dengan 3 tab: Notes, Favorites, Profile
2. **Note List → Note Detail** navigation dengan passing `noteId`
3. **Floating Action Button** untuk Add Note (navigate ke `AddNoteScreen`)
4. **Back navigation** yang proper dari semua screen
5. **Edit Note screen** dengan passing `noteId` sebagai argument

---

## 🗂️ Struktur Folder

```
app/src/main/java/com/example/myprofileapp/
├── data/
│   ├── NoteUiState.kt          # Data class Note + dummy data
│   └── ProfileUiState.kt       # Data class Profile
├── navigation/
│   ├── Screen.kt               # Sealed class routes + BottomNavItem
│   └── AppNavGraph.kt          # NavHost dengan semua route
├── screens/
│   ├── NoteListScreen.kt       # Daftar catatan + FAB
│   ├── NoteDetailScreen.kt     # Detail catatan + aksi edit/delete
│   ├── AddNoteScreen.kt        # Form tambah catatan baru
│   ├── EditNoteScreen.kt       # Form edit catatan (noteId sebagai argument)
│   ├── FavoritesScreen.kt      # Daftar catatan yang difavoritkan
│   └── ProfileScreen.kt        # Profil pengguna (refactor dari minggu lalu)
├── components/
│   └── NoteCard.kt             # Reusable card component
├── viewmodel/
│   ├── NoteViewModel.kt        # CRUD catatan (in-memory)
│   └── ProfileViewModel.kt     # Manajemen state profil
├── ui/theme/
│   ├── Color.kt
│   ├── Theme.kt
│   └── Type.kt
└── MainActivity.kt             # Entry point + Bottom Nav Scaffold
```

---

## 🧭 Navigation Flow

```
MainActivity
    └── MainApp (Scaffold + Bottom Navigation)
            │
            ├── [Tab 1] NoteListScreen
            │       ├── tap note card  ──→  NoteDetailScreen (noteId)
            │       │                           └── tap edit  ──→  EditNoteScreen (noteId)
            │       │                           └── back      ──→  NoteListScreen
            │       └── tap FAB (+)    ──→  AddNoteScreen
            │                               └── back/save  ──→  NoteListScreen
            │
            ├── [Tab 2] FavoritesScreen
            │       └── tap note card  ──→  NoteDetailScreen (noteId)
            │
            └── [Tab 3] ProfileScreen
                        └── tap Edit Profile  ──→  inline edit (tanpa navigasi)
```

### Cara Passing Data Antar Screen

Navigasi ke Note Detail dilakukan dengan cara berikut:

```kotlin
// Definisi route dengan argument
object NoteDetail : Screen("note_detail/{noteId}") {
    fun createRoute(noteId: Int) = "note_detail/$noteId"
}

// Navigate dengan membawa noteId
navController.navigate(Screen.NoteDetail.createRoute(noteId))

// Menerima argument di destination
composable(
    route = Screen.NoteDetail.route,
    arguments = listOf(navArgument("noteId") { type = NavType.IntType })
) { backStackEntry ->
    val noteId = backStackEntry.arguments?.getInt("noteId")
    NoteDetailScreen(noteId = noteId, ...)
}
```

---

## ✨ Fitur Aplikasi

### Notes Tab
- Menampilkan daftar semua catatan dalam `LazyColumn`
- Tap kartu catatan → navigasi ke detail
- Tap ikon ♡ → toggle favorit langsung dari list
- FAB (+) di pojok kanan bawah → navigasi ke form tambah catatan

### Favorites Tab
- Menampilkan catatan yang ditandai sebagai favorit
- Filter otomatis dari `NoteViewModel` (tidak ada query terpisah)
- Empty state saat belum ada catatan favorit

### Profile Tab
- Menampilkan profil pengguna
- Toggle dark/light mode
- Edit nama dan bio secara inline (tanpa navigasi keluar dari tab)

### Note Detail Screen
- Menampilkan judul dan isi catatan lengkap
- Badge "Favorit" jika catatan difavoritkan
- Tombol edit di top bar → navigasi ke EditNoteScreen
- Tombol delete → hapus catatan dan kembali ke list
- Back button → kembali ke screen sebelumnya

### Add & Edit Note Screen
- Form dengan field judul dan isi catatan
- Tombol Simpan → menyimpan data dan kembali
- Tombol Batal → kembali tanpa menyimpan
- EditNoteScreen menerima `noteId` dan pre-fill data dari ViewModel

---

## 🛠️ Teknologi

| Teknologi | Versi |
|---|---|
| Kotlin | 1.9.x |
| Jetpack Compose | BOM 2024.x |
| Navigation Compose | 2.7.7 |
| Lifecycle ViewModel Compose | 2.8.7 |
| Lifecycle Runtime Compose | 2.8.7 |
| Material 3 | - |

---

## 📦 Dependencies (build.gradle.kts)

```kotlin
implementation("androidx.navigation:navigation-compose:2.7.7")
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
```

---

## 📸 Screenshot

| NoteList | NoteDetail | AddNote |
|---|---|---|
| /screenshot_Video/mynotes | /screenshot_Video/detail | /screenshot_Video/add |

| Favorites | EditNote | Profile |
|---|---|---|
| /screenshot_Video/fav | /screenshot_Video/edit | /screenshot_Video/profile |


---

## 🎬 Demo

Video demo (30 detik) menunjukkan semua navigation flows tersedia di: /screenshot_Video/vid_demo

Flow yang didemonstrasikan:
1. Bottom nav switching (Notes → Favorites → Profile)
2. Tap note → detail screen
3. FAB → tambah catatan baru → kembali ke list
4. Edit catatan dari detail screen
5. Toggle favorit dan lihat di tab Favorites