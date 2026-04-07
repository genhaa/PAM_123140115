# Tugas 2: Advanced Kotlin, Coroutines, dan Flow
**Mata Kuliah:** Pengembangan Aplikasi Mobile (PAM) - ITERA  
**Nama:** Grace Exauditha Nababan 
**NIM:** 123140115

---

## 📄 Deskripsi Proyek
Proyek ini adalah simulasi **News Simulator** yang mengimplementasikan pemrograman *asynchronous* menggunakan **Kotlin Coroutines** dan **Kotlin Flow**. Aplikasi ini mensimulasikan aliran data berita secara *real-time* dengan berbagai transformasi data sebelum ditampilkan ke pengguna.

## 🛠️ Fitur & Implementasi (Rubrik Penilaian)

Sesuai dengan kriteria penilaian tugas pertemuan 2, proyek ini mencakup:

1.  **Implementasi Flow (25%)**: 
    - Menggunakan `flow { ... }` sebagai producer data berita.
    - Menggunakan `emit()` untuk mengirimkan data secara berkala.
    - Menggunakan `collect()` untuk mengonsumsi data di sisi UI/Main.

2.  **Penggunaan Operators**: 
    - `onEach`: Melakukan log setiap berita yang masuk.
    - `filter`: Menyaring berita berdasarkan kriteria tertentu (misal: kategori atau kata kunci).
    - `map`: Mengubah data berita menjadi format "Hot News" (Upper Case).

3.  **State Flow Implementation**: 
    - Menggunakan `MutableStateFlow` untuk menyimpan status aplikasi (Loading, Success, Idle) agar data tetap konsisten saat terjadi perubahan state.

4.  **Coroutines Usage**: 
    - Implementasi `launch` dengan `Dispatchers.Default` untuk menjalankan proses di background thread.
    - Penggunaan `delay()` untuk simulasi proses pengambilan data asinkron.

5.  **Clean Code & Documentation**: 
    - Kode dipisah secara modular (Model, Repository, Main).
    - Penamaan variabel yang deskriptif dan mengikuti standar Kotlin Style Guide.

---

## 🚀 Cara Menjalankan Proyek

1. **Clone/Download** repository ini.
2. Buka di **Android Studio (Koala atau versi terbaru)**.
3. Tunggu proses **Gradle Sync** selesai.
4. **Menjalankan Logic Utama:**
   - Cari file `Main.kt` di `src/commonMain/kotlin/`.
   - Klik ikon *Play* hijau di sebelah `fun main()`.
5. **Menjalankan Unit Test (Bonus):**
   - Buka folder `src/commonTest/kotlin/`.
   - Jalankan `NewsTest.kt` untuk melihat verifikasi aliran data di tab **Run**.

