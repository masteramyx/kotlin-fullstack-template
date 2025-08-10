package com.template.plugins

import com.template.db.DatabaseFactory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import java.io.File
import kotlinx.serialization.Serializable

@Serializable
data class HelloResponse(
    val message: String,
    val timestamp: String
)

@Serializable
data class HealthStatus(
    val status: String,
    val version: String,
    val database: String,
    val timestamp: String
)

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(HelloResponse(
                message = "Hello World from Kotlin Fullstack Template!",
                timestamp = java.time.Instant.now().toString()
            ))
        }
        
        // Serve the compiled JavaScript and other static assets for the frontend
        // This serves the Compose Web frontend when it's built
        val dockerStaticPath = File("/app/web/build/kotlin-webpack/js/productionExecutable")
        val localStaticPath = File("../web/build/kotlin-webpack/js/developmentExecutable")
        val staticPath = if (dockerStaticPath.exists()) dockerStaticPath else localStaticPath
        staticFiles("/static", staticPath)
        
        // Serve the HTML file for the frontend
        get("/app") {
            val dockerPath = File("/app/web/build/processedResources/js/main/index.html")
            val localPath = File("../web/build/processedResources/js/main/index.html")
            val indexFile = if (dockerPath.exists()) dockerPath else localPath
            call.respondFile(indexFile)
        }
        
        get("/health") {
            try {
                // Test database connection by querying users table
                val database = DatabaseFactory.getDatabase()
                val userQueries = database.userQueries
                userQueries.getAllUsers().executeAsList()
                
                call.respond(HttpStatusCode.OK, HealthStatus(
                    status = "healthy",
                    version = "1.0.0",
                    database = "connected",
                    timestamp = java.time.Instant.now().toString()
                ))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.ServiceUnavailable, HealthStatus(
                    status = "unhealthy",
                    version = "1.0.0", 
                    database = "disconnected - ${e.message}",
                    timestamp = java.time.Instant.now().toString()
                ))
            }
        }
    }
}