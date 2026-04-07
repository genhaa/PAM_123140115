import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import model.News
import repository.NewsRepository

fun main() = runBlocking {
    val repo = NewsRepository()

    val latestNews = MutableStateFlow<News?>(null)

    println("Menunggu berita terbaru...")

    launch(Dispatchers.Default) {
        repo.streamBreakingNews()
            .onEach { println("Notif masuk: ${it.title}") }
            .filter { it.title.contains("KMP") }
            .map { "HOT NEWS: ${it.title}" }
            .collect { hasil ->
                println("Tampilkan di Layar: $hasil")
            }
    }
}
