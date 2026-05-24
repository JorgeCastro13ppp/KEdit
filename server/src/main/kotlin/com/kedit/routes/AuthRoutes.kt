package com.kedit.routes

import com.kedit.repository.UserRepository
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receiveText
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.authRoutes(
    userRepository: UserRepository
) {

    post("/auth/register") {

        val body =
            call.receiveText()

        val email =
            extractJsonValue(
                body = body,
                key = "email"
            )

        val password =
            extractJsonValue(
                body = body,
                key = "password"
            )

        val user =
            userRepository.createUser(
                email = email,
                password = password
            )

        if (user == null) {

            call.respondText(
                text =
                    """
                    {
                        "message": "El usuario ya existe"
                    }
                    """.trimIndent(),

                contentType = ContentType.Application.Json,
                status = HttpStatusCode.Conflict
            )

        } else {

            call.respondText(
                text =
                    """
                    {
                        "userId": ${user.id},
                        "email": "${user.email}",
                        "message": "Usuario registrado correctamente"
                    }
                    """.trimIndent(),

                contentType = ContentType.Application.Json,
                status = HttpStatusCode.Created
            )
        }
    }

    post("/auth/login") {

        val body =
            call.receiveText()

        val email =
            extractJsonValue(
                body = body,
                key = "email"
            )

        val password =
            extractJsonValue(
                body = body,
                key = "password"
            )

        val user =
            userRepository.login(
                email = email,
                password = password
            )

        if (user == null) {

            call.respondText(
                text =
                    """
                    {
                        "message": "Credenciales incorrectas"
                    }
                    """.trimIndent(),

                contentType = ContentType.Application.Json,
                status = HttpStatusCode.Unauthorized
            )

        } else {

            call.respondText(
                text =
                    """
                    {
                        "userId": ${user.id},
                        "email": "${user.email}",
                        "message": "Login correcto"
                    }
                    """.trimIndent(),

                contentType = ContentType.Application.Json
            )
        }
    }
}