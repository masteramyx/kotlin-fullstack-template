package com.template

import io.ktor.server.application.*
import com.template.plugins.*
import com.template.db.DatabaseFactory
import com.template.utils.logger

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    logger.info("Starting Kotlin Fullstack Template application")
    
    // Initialize database connection
    DatabaseFactory.init()
    logger.info("Database initialized successfully")
    
    configureSerialization()
    configureCORS()
    configureRouting()
    
    logger.info("Application configuration complete")
}