import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun String.tambahSeru() = this + "!!!"

fun ambilDataFlow() = flow {
    emit("Lagi loading...")
    delay(1000)
    emit("Data Berhasil!")
}

fun runTugas2() = runBlocking {
    launch {
        ambilDataFlow().collect { data ->
            println(data.tambahSeru())
        }
    }
}
