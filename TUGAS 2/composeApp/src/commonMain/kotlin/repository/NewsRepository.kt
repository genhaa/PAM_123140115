import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class NewsRepository {
    fun streamBreakingNews(): Flow<News> = flow {
        val newsTitles = listOf("Kotlin Flow vs RxJava", "Update Android 15")

        while(true) {
            for (title in newsTitles) {
                delay(2000)
                emit(News(title, "Tech", System.currentTimeMillis()))
            }
        }
    }.catch { e -> println("Gagal ambil berita: ${e.message}") } 
}