import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import org.jetbrains.compose.web.css.*
import kotlinx.coroutines.launch

fun main() {
    renderComposable(rootElementId = "root") {
        App()
    }
}

@Composable
fun App() {
    var message by remember { mutableStateOf("Loading...") }

    // Try to fetch hello message from backend, fallback to static message
    LaunchedEffect(Unit) {
        try {
            // For now, just show static message since we simplified the backend
            message = "Hello World from Kotlin Fullstack Template!"
        } catch (e: Exception) {
            console.error("Failed to fetch message:", e)
            message = "Hello World from Kotlin Fullstack Template!"
        }
    }

    Div {
        H1 { Text("Kotlin Fullstack Template") }
        P { Text(message) }
        P { Text("This is a template for building Kotlin Multiplatform web applications.") }
        P { Text("Check the README.md for setup instructions.") }
    }
}