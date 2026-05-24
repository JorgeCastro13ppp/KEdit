package com.kedit

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.receiveText
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
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

    routing {

        get("/") {
            call.respondText("KEdit backend funcionando")
        }

        post("/auth/register") {

            val body =
                call.receiveText()

            call.respondText(
                text =
                    """
                    {
                        "userId": 1,
                        "email": "demo@kedit.com",
                        "message": "Usuario registrado correctamente"
                    }
                    """.trimIndent(),

                contentType = ContentType.Application.Json,
                status = HttpStatusCode.Created
            )
        }

        post("/auth/login") {

            val body =
                call.receiveText()

            call.respondText(
                text =
                    """
                    {
                        "userId": 1,
                        "email": "demo@kedit.com",
                        "message": "Login correcto"
                    }
                    """.trimIndent(),

                contentType = ContentType.Application.Json
            )
        }
    }
}