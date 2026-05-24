package com.kedit

import com.kedit.database.DatabaseFactory
import com.kedit.repository.DocumentRepository
import com.kedit.repository.UserRepository
import com.kedit.routes.authRoutes
import com.kedit.routes.documentRoutes
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8082,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {

    DatabaseFactory.init()

    val userRepository =
        UserRepository()

    val documentRepository =
        DocumentRepository()

    routing {

        get("/") {
            call.respondText("KEdit backend funcionando")
        }

        authRoutes(
            userRepository = userRepository
        )

        documentRoutes(
            documentRepository = documentRepository
        )
    }
}